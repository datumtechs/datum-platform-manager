package com.platon.rosettaflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platon.rosettaflow.common.enums.*;
import com.platon.rosettaflow.common.exception.BusinessException;
import com.platon.rosettaflow.dto.AlgorithmDto;
import com.platon.rosettaflow.dto.WorkflowNodeDto;
import com.platon.rosettaflow.grpc.identity.dto.NodeIdentityDto;
import com.platon.rosettaflow.grpc.service.GrpcAuthService;
import com.platon.rosettaflow.mapper.WorkflowNodeMapper;
import com.platon.rosettaflow.mapper.domain.*;
import com.platon.rosettaflow.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工作流节点服务实现类
 *
 * @author hudenian
 * @date 2021/8/31
 */
@Slf4j
@Service
public class WorkflowNodeServiceImpl extends ServiceImpl<WorkflowNodeMapper, WorkflowNode> implements IWorkflowNodeService {

    @Resource
    private IProjectService projectService;

    @Resource
    private IWorkflowService workflowService;

    @Resource
    private IAlgorithmService algorithmService;

    @Resource
    private IJobService jobService;

    @Resource
    private IWorkflowNodeCodeService workflowNodeCodeService;

    @Resource
    private IWorkflowNodeInputService workflowNodeInputService;

    @Resource
    private IWorkflowNodeOutputService workflowNodeOutputService;

    @Resource
    private IWorkflowNodeVariableService workflowNodeVariableService;

    @Resource
    private IWorkflowNodeResourceService workflowNodeResourceService;

    @Resource
    private GrpcAuthService grpcAuthService;

    @Resource
    private IOrganizationService organizationService;

    @Override
    public List<WorkflowNodeDto> queryNodeDetailsList(Long id) {
        // 获取工作流节点列表
        List<WorkflowNode> workflowNodeList = getWorkflowNodeList(id);
        if (workflowNodeList == null || workflowNodeList.size() == 0) {
            return new ArrayList<>();
        }
        List<WorkflowNodeDto> workflowNodeDtoList = new ArrayList<>();
        for (WorkflowNode workflowNode : workflowNodeList) {
            // 工作流节点dto
            WorkflowNodeDto workflowNodeDto = BeanUtil.toBean(workflowNode, WorkflowNodeDto.class);
            // 算法对象
            AlgorithmDto algorithmDto = algorithmService.queryAlgorithmDetails(workflowNode.getAlgorithmId());
            if (Objects.nonNull(algorithmDto)) {
                // 工作流节点算法代码, 如果可查询出，表示已修改，否则没有变动
                WorkflowNodeCode workflowNodeCode = workflowNodeCodeService.getByWorkflowNodeId(workflowNode.getId());
                if (Objects.nonNull(workflowNodeCode)) {
                    algorithmDto.setEditType(workflowNodeCode.getEditType());
                    algorithmDto.setCalculateContractCode(workflowNodeCode.getCalculateContractCode());
                }
                // 工作流节点算法资源环境, 如果可查询出，表示已修改，否则没有变动
                WorkflowNodeResource nodeResource = workflowNodeResourceService.getByWorkflowNodeId(workflowNode.getId());
                if (Objects.nonNull(nodeResource)) {
                    algorithmDto.setCostCpu(nodeResource.getCostCpu());
                    algorithmDto.setCostGpu(nodeResource.getCostGpu());
                    algorithmDto.setCostMem(nodeResource.getCostMem());
                    algorithmDto.setCostBandwidth(nodeResource.getCostBandwidth());
                    algorithmDto.setRunTime(nodeResource.getRunTime());
                }
            }
            workflowNodeDto.setAlgorithmDto(algorithmDto);
            //工作流节点输入列表
            List<WorkflowNodeInput> workflowNodeInputList = workflowNodeInputService.getByWorkflowNodeId(workflowNode.getId());
            workflowNodeDto.setWorkflowNodeInputList(workflowNodeInputList);
            //工作流节点输出列表
            List<WorkflowNodeOutput> workflowNodeOutputList = workflowNodeOutputService.getByWorkflowNodeId(workflowNode.getId());
            workflowNodeDto.setWorkflowNodeOutputList(workflowNodeOutputList);
            workflowNodeDtoList.add(workflowNodeDto);
        }
        return workflowNodeDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveWorkflowAllNodeData(Long workflowId, List<WorkflowNodeDto> workflowNodeDtoList) {
        Workflow workflow = workflowService.queryWorkflowDetail(workflowId);

        // 校验是否有编辑权限
        checkEditPermission(workflow.getProjectId());

        //判断当前工作流是否存在正在运行或者有加入正在运行的作业中，有则不让保存
        if (workflow.getRunStatus() == WorkflowRunStatusEnum.RUNNING.getValue()) {
            log.info("saveWorkflowNode--工作流运行中:{}", JSON.toJSONString(workflow));
            throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.WORKFLOW_RUNNING_EXIST.getMsg());
        }

        // 第一期项目只允许保存一个节点
        if (workflowNodeDtoList.size() > 1) {
            log.info("saveWorkflowNode--工作流节点超出范围:{}", JSON.toJSONString(workflowNodeDtoList));
            throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.WORKFLOW_NODE_COUNT_CHECK.getMsg());
        }

        //判断正在运行的作业是否包含此工作流
        List<Job> jobList = jobService.listRunJobByWorkflowId(workflowId);
        if (null != jobList && jobList.size() > 0) {
            throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.WORKFLOW_RUNNING_EXIST.getMsg());
        }

        // 删除当前工作流所有节点数据
        workflowService.deleteWorkflowAllNodeData(workflowId);

        //加入所有工作流新节点
        List<WorkflowNodeInput> workflowNodeInputList = new ArrayList<>();
        List<WorkflowNodeOutput> workflowNodeOutputList = new ArrayList<>();
        List<WorkflowNodeCode> workflowNodeCodeList = new ArrayList<>();
        List<WorkflowNodeResource> workflowNodeResourceList = new ArrayList<>();
        List<WorkflowNodeVariable> workflowNodeVariableList = new ArrayList<>();
        int count = 0;
        for (WorkflowNodeDto workflowNodeDto : workflowNodeDtoList) {
            //保存工作流节点
            WorkflowNode workflowNode = new WorkflowNode();
            workflowNode.setWorkflowId(workflowId);
            workflowNode.setNodeName(workflowNodeDto.getNodeName());
            workflowNode.setAlgorithmId(workflowNodeDto.getAlgorithmId());
            workflowNode.setNodeStep(workflowNodeDto.getNodeStep());
            workflowNode.setNextNodeStep(workflowNodeDto.getNodeStep() + 1);
            if (++count == workflowNodeDtoList.size()) {
                // 将最后一个节点步骤的下一节点步骤字段值置空
                workflowNode.setNextNodeStep(null);
            }
            this.save(workflowNode);
            //添加新的工作流节点输入
            int senderNum = 0;
            for (int i = 0; i < workflowNodeDto.getWorkflowNodeInputList().size(); i++) {
                //添加节点输入
                WorkflowNodeInput workflowNodeInput = new WorkflowNodeInput();
                workflowNodeInput.setWorkflowNodeId(workflowNode.getId());
                workflowNodeInput.setIdentityId(workflowNodeDto.getWorkflowNodeInputList().get(i).getIdentityId());
                workflowNodeInput.setDataTableId(workflowNodeDto.getWorkflowNodeInputList().get(i).getDataTableId());
                workflowNodeInput.setDataColumnIds(workflowNodeDto.getWorkflowNodeInputList().get(i).getDataColumnIds());
                workflowNodeInput.setSenderFlag(workflowNodeDto.getWorkflowNodeInputList().get(i).getSenderFlag());
                workflowNodeInput.setPartyId("p" + i);
                workflowNodeInputList.add(workflowNodeInput);

                //发起方默认保存
                if (workflowNodeDto.getWorkflowNodeInputList().get(i).getSenderFlag() == SenderFlagEnum.TRUE.getValue()) {
                    senderNum++;
                    WorkflowNodeOutput workflowNodeOutput = new WorkflowNodeOutput();
                    workflowNodeOutput.setWorkflowNodeId(workflowNode.getId());
                    workflowNodeOutput.setIdentityId(workflowNodeDto.getWorkflowNodeInputList().get(i).getIdentityId());
                    workflowNodeOutput.setPartyId("p" + i);
                    workflowNodeOutput.setSenderFlag(workflowNodeDto.getWorkflowNodeInputList().get(i).getSenderFlag());
                    workflowNodeOutputList.add(workflowNodeOutput);
                }
            }
            if (senderNum != 1) {
                throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.WORKFLOW_NODE_SENDER_NOT_EXIST.getMsg());
            }

            //添加新的工作流节点输出
            for (int i = 0; i < workflowNodeDto.getWorkflowNodeInputList().size(); i++) {
                WorkflowNodeOutput workflowNodeOutput = new WorkflowNodeOutput();
                workflowNodeOutput.setWorkflowNodeId(workflowNode.getId());
                workflowNodeOutput.setIdentityId(workflowNodeDto.getWorkflowNodeInputList().get(i).getIdentityId());
                workflowNodeOutput.setPartyId("p" + i + 1);
                workflowNodeOutput.setSenderFlag(SenderFlagEnum.FALSE.getValue());
                workflowNodeOutputList.add(workflowNodeOutput);
            }
            //添加新的工作流节点代码
            if (Objects.nonNull(workflowNodeDto.getWorkflowNodeCode())) {
                workflowNodeDto.getWorkflowNodeCode().setWorkflowNodeId(workflowNode.getId());
                workflowNodeCodeList.add(workflowNodeDto.getWorkflowNodeCode());
            }
            //添加新的工作流节点资源
            if (Objects.nonNull(workflowNodeDto.getWorkflowNodeResource())) {
                workflowNodeDto.getWorkflowNodeResource().setWorkflowNodeId(workflowNode.getId());
                workflowNodeResourceList.add(workflowNodeDto.getWorkflowNodeResource());
            }
            //添加新的工作流节点变量
            List<WorkflowNodeVariable> workflowNodeVariableDtoList = workflowNodeDto.getWorkflowNodeVariableList();
            if (null != workflowNodeVariableDtoList && workflowNodeVariableDtoList.size() > 0) {
                workflowNodeVariableDtoList.forEach(workflowNodeVariable -> {
                    workflowNodeVariable.setWorkflowNodeId(workflowNode.getId());
                });
                workflowNodeVariableList.addAll(workflowNodeVariableDtoList);
            }
        }
        // 保存节点相关数据
        saveNodeData(workflowNodeInputList, workflowNodeOutputList, workflowNodeCodeList,
                workflowNodeResourceList, workflowNodeVariableList);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void clearWorkflowNode(Long workflowId) {
        Workflow workflow = workflowService.queryWorkflowDetail(workflowId);
        // 校验是否有编辑权限
        checkEditPermission(workflow.getProjectId());
        List<WorkflowNode> workflowNodeList = getAllWorkflowNodeList(workflowId);
        if (workflowNodeList == null || workflowNodeList.size() == 0) {
            return;
        }
        // 物理删除当前工作流所有节点数据
       workflowService.deleteWorkflowAllNodeData(workflowId);
    }

    @Override
    public WorkflowNode getByWorkflowIdAndStep(Long workflowId, Integer nodeStep) {
        LambdaQueryWrapper<WorkflowNode> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkflowNode::getWorkflowId, workflowId);
        wrapper.eq(WorkflowNode::getNodeStep, nodeStep);
        wrapper.eq(WorkflowNode::getStatus, StatusEnum.VALID.getValue());
        WorkflowNode workflowNode = this.getOne(wrapper);
        if (workflowNode == null) {
            log.error("workflow node not found by workflowId:{},nodeStep:{}", workflowId, nodeStep);
            throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.WORKFLOW_NODE_NOT_EXIST.getMsg());
        }
        return workflowNode;
    }

    @Override
    public List<WorkflowNode> getAllWorkflowNodeList(Long workflowId) {
        // 查询所有节点（包含失效数据）
        LambdaQueryWrapper<WorkflowNode> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkflowNode::getWorkflowId, workflowId);
        // 所有节点正序排序
        wrapper.orderByAsc(WorkflowNode::getNodeStep);
        return this.list(wrapper);
    }

    @Override
    public List<WorkflowNode> getWorkflowNodeList(Long workflowId) {
        LambdaQueryWrapper<WorkflowNode> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkflowNode::getWorkflowId, workflowId);
        wrapper.eq(WorkflowNode::getStatus, StatusEnum.VALID.getValue());
        // 所有节点正序排序
        wrapper.orderByAsc(WorkflowNode::getNodeStep);
        return this.list(wrapper);
    }

    @Override
    public WorkflowNode getWorkflowNodeById(Long id) {
        LambdaQueryWrapper<WorkflowNode> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkflowNode::getId, id);
        wrapper.eq(WorkflowNode::getStatus, StatusEnum.VALID.getValue());
        return this.getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveWorkflowNodeInput(Long workflowNodeId, List<WorkflowNodeInput> workflowNodeInputList) {
        String[] identityIdArr = new String[workflowNodeInputList.size()];
        List<WorkflowNodeInput> nodeInputList =
                workflowNodeInputService.getByWorkflowNodeId(workflowNodeId);
        // 如果已存在则全部删除，并新增
        if (nodeInputList != null && nodeInputList.size() > 0) {
            List<Long> idList = new ArrayList<>();
            nodeInputList.forEach(nodeInput -> idList.add(nodeInput.getId()));
            // 物理删除
            workflowNodeInputService.removeByIds(idList);
        }

        //任务里面定义的 (p0 -> pN 方 ...)
        for (int i = 0; i < workflowNodeInputList.size(); i++) {
            workflowNodeInputList.get(i).setPartyId("p" + i);
            identityIdArr[i] = workflowNodeInputList.get(i).getIdentityId();
        }

        //校验组织信息
        List<Organization> organizationList = organizationService.getByIdentityIds(identityIdArr);
        if (null == organizationList || organizationList.size() != identityIdArr.length) {
            List<Organization> newOrganizationList = syncOrganization();
            Set<String> identityIdSet = new HashSet<>();
            newOrganizationList.forEach(o -> identityIdSet.add(o.getIdentityId()));
            for (String identity : identityIdArr) {
                if (!identityIdSet.contains(identity)) {
                    throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.ORGANIZATION_NOT_EXIST.getMsg());
                }
            }
        }

        // 新增
        workflowNodeInputService.batchInsert(workflowNodeInputList);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveWorkflowNodeOutput(Long workflowNodeId, List<WorkflowNodeOutput> workflowNodeOutputList) {
        List<WorkflowNodeOutput> nodeOutputList =
                workflowNodeOutputService.getByWorkflowNodeId(workflowNodeId);
        // 如果已存在则全部删除，并新增
        if (nodeOutputList != null && nodeOutputList.size() > 0) {
            List<Long> idList = new ArrayList<>();
            for (WorkflowNodeOutput nodeInput : nodeOutputList) {
                idList.add(nodeInput.getId());
            }
            // 物理删除
            workflowNodeOutputService.removeByIds(idList);
        }
        //任务里面定义的 (p0 -> pN 方 ...) 与输入保持一致
        List<WorkflowNodeInput> workflowNodeInputs = workflowNodeInputService.getByWorkflowNodeId(workflowNodeOutputList.get(0).getWorkflowNodeId());
        Map<String, String> partyIdMap = workflowNodeInputs.stream().collect(Collectors.toMap(WorkflowNodeInput::getIdentityId, WorkflowNodeInput::getPartyId));
        for (WorkflowNodeOutput workflowNodeOutput : workflowNodeOutputList) {
            workflowNodeOutput.setPartyId(partyIdMap.get(workflowNodeOutput.getIdentityId()));
        }

        // 新增
        workflowNodeOutputService.saveBatch(workflowNodeOutputList);
    }

    @Override
    public void saveCopyWorkflowNode(Long newWorkflowId, List<WorkflowNode> oldNodeList) {
        if (null == oldNodeList || oldNodeList.size() == 0) {
            return;
        }
        List<WorkflowNodeInput> newNodeInputList = new ArrayList<>();
        List<WorkflowNodeOutput> newNodeOutputList = new ArrayList<>();
        List<WorkflowNodeCode> newNodeCodeList = new ArrayList<>();
        List<WorkflowNodeResource> newNodeResourceList = new ArrayList<>();
        List<WorkflowNodeVariable> newNodeVariableList = new ArrayList<>();
        oldNodeList.forEach(oldNode -> {
            WorkflowNode newNode = new WorkflowNode();
            newNode.setWorkflowId(newWorkflowId);
            newNode.setAlgorithmId(oldNode.getAlgorithmId());
            newNode.setNodeName(oldNode.getNodeName());
            newNode.setNodeStep(oldNode.getNodeStep());
            newNode.setNextNodeStep(oldNode.getNextNodeStep());
            this.save(newNode);
            // 复制节点输入数据
            List<WorkflowNodeInput> workflowNodeInputList = workflowNodeInputService.copyWorkflowNodeInput(newNode.getId(), oldNode.getId());
            newNodeInputList.addAll(workflowNodeInputList);
            // 复制节点输出数据
            List<WorkflowNodeOutput> workflowNodeOutputList = workflowNodeOutputService.copyWorkflowNodeOutput(newNode.getId(), oldNode.getId());
            newNodeOutputList.addAll(workflowNodeOutputList);
            // 复制节点算法代码
            WorkflowNodeCode workflowNodeCode = workflowNodeCodeService.copyWorkflowNodeCode(newNode.getId(), oldNode.getId());
            if (Objects.nonNull(workflowNodeCode)) {
                newNodeCodeList.add(workflowNodeCode);
            }
            // 复制节点环境资源
            WorkflowNodeResource workflowNodeResource = workflowNodeResourceService.copyWorkflowNodeResource(newNode.getId(), oldNode.getId());
            if (Objects.nonNull(workflowNodeResource)) {
                newNodeResourceList.add(workflowNodeResource);
            }
            // 复制节点变量
            List<WorkflowNodeVariable> workflowNodeVariableList = workflowNodeVariableService.copyWorkflowNodeVariable(newNode.getId(), oldNode.getId());
            if (Objects.nonNull(workflowNodeResource)) {
                workflowNodeVariableList.addAll(workflowNodeVariableList);
            }
        });
        // 保存节点相关数据
        saveNodeData(newNodeInputList, newNodeOutputList, newNodeCodeList,
                newNodeResourceList, newNodeVariableList);
    }

    /** 保存节点相关数据 */
    private void saveNodeData(List<WorkflowNodeInput> newNodeInputList,
                              List<WorkflowNodeOutput> newNodeOutputList,
                              List<WorkflowNodeCode> newNodeCodeList,
                              List<WorkflowNodeResource> newNodeResourceList,
                              List<WorkflowNodeVariable> newNodeVariableList
                              ){
        // 保存节点输入数据
        if (newNodeInputList.size() > 0) {
            workflowNodeInputService.batchInsert(newNodeInputList);
        }
        // 保存节点输出数据
        if (newNodeOutputList.size() > 0) {
            workflowNodeOutputService.batchInsert(newNodeOutputList);
        }
        // 保存节点算法代码
        if (newNodeCodeList.size() > 0) {
            workflowNodeCodeService.batchInsert(newNodeCodeList);
        }
        // 保存节点环境资源
        if (newNodeResourceList.size() > 0) {
            workflowNodeResourceService.batchInsert(newNodeResourceList);
        }
        // 保存节点变量
        if (newNodeVariableList.size() > 0) {
            workflowNodeVariableService.batchInsert(newNodeVariableList);
        }
    }

    @Override
    public void addWorkflowNodeByTemplate(Long workflowId, List<WorkflowNodeTemp> workflowNodeTempList) {
        List<WorkflowNode> oldNodeList = BeanUtil.copyToList(workflowNodeTempList, WorkflowNode.class);
        saveCopyWorkflowNode(workflowId, oldNodeList);
    }

    @Override
    public WorkflowNode getRunningNodeByWorkflowId(Long workflowId) {
        LambdaQueryWrapper<WorkflowNode> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkflowNode::getWorkflowId, workflowId);
        wrapper.eq(WorkflowNode::getRunStatus, WorkflowRunStatusEnum.RUNNING.getValue());
        wrapper.eq(WorkflowNode::getStatus, StatusEnum.VALID.getValue());
        return this.getOne(wrapper);
    }

    @Override
    public void updateRunStatusByWorkflowId(Long workflowId, Byte oldRunStatus, Byte newRunStatus) {
        LambdaUpdateWrapper<WorkflowNode> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(WorkflowNode::getRunStatus, newRunStatus);
        updateWrapper.eq(WorkflowNode::getRunStatus, oldRunStatus);
        updateWrapper.eq(WorkflowNode::getWorkflowId, workflowId);
        updateWrapper.eq(WorkflowNode::getStatus, StatusEnum.VALID.getValue());
        this.update(updateWrapper);
    }

    /**
     * 校验是否有编辑权限
     */
    private void checkEditPermission(Long projectId) {
        Byte role = projectService.getRoleByProjectId(projectId);
        if (null == role || ProjectMemberRoleEnum.VIEW.getRoleId() == role) {
            throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.USER_NOT_PERMISSION_ERROR.getMsg());
        }
    }

    /**
     * 同步组织信息
     *
     * @return 组织信息列表
     */
    private List<Organization> syncOrganization() {
        List<Organization> organizationList = new ArrayList<>();
        List<NodeIdentityDto> nodeIdentityDtoList = grpcAuthService.getIdentityList();
        if (null != nodeIdentityDtoList && nodeIdentityDtoList.size() > 0) {

            Organization org;
            for (NodeIdentityDto nodeIdentityDto : nodeIdentityDtoList) {
                org = new Organization();
                org.setNodeName(nodeIdentityDto.getNodeName());
                org.setNodeId(nodeIdentityDto.getNodeId());
                org.setIdentityId(nodeIdentityDto.getIdentityId());
                org.setStatus(nodeIdentityDto.getStatus().byteValue());
                organizationList.add(org);
            }
            organizationService.batchInsert(organizationList);
        }
        return organizationList;
    }
}
