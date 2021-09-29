package com.platon.rosettaflow.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platon.rosettaflow.dto.AlgorithmDto;
import com.platon.rosettaflow.dto.WorkflowNodeDto;
import com.platon.rosettaflow.mapper.domain.*;
import com.platon.rosettaflow.req.workflow.node.WorkflowNodeReq;
import com.platon.rosettaflow.vo.PageVo;
import com.platon.rosettaflow.vo.workflow.node.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author admin
 * @date 2021/8/16
 * @description 功能描述
 */
public class ConvertUtils {

    public static <T> PageVo<T> convertPageVo(IPage<?> page, List<T> items) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setCurrent(page.getCurrent());
        pageVo.setItems(items);
        pageVo.setSize(page.getSize());
        pageVo.setTotal(page.getTotal());
        return pageVo;
    }

    /**
     * 列表对象并行转换工具(性能好，会乱序)
     *
     * @param list1  源列表数据
     * @param tClass 目标类
     * @return 目标类列表
     */
    @SuppressWarnings("unused")
    public static <T> List<T> convertParallelToList(List<?> list1, Class<T> tClass) {
        List<T> list2 = new ArrayList<>();
        list1.parallelStream().forEach(o1 -> {
            T target = ReflectUtil.newInstanceIfPossible(tClass);
            BeanUtils.copyProperties(o1, target);
            list2.add(target);
        });
        return list2;
    }

    /** 转换保存请求参数 */
    public static List<WorkflowNodeDto> convertSaveReq(List<WorkflowNodeReq> workflowNodeReqList) {
        if(workflowNodeReqList.size() == 0) {
            return new ArrayList<>();
        }
        List<WorkflowNodeDto> workflowNodeDtoList = new ArrayList<>();
        workflowNodeReqList.forEach(workflowNodeReq -> {
            WorkflowNodeDto workflowNodeDto = new WorkflowNodeDto();
            // 节点输入
            if (workflowNodeReq.getWorkflowNodeInputReqList().size() > 0) {
                workflowNodeDto.setWorkflowNodeInputList(BeanUtil.copyToList(
                        workflowNodeReq.getWorkflowNodeInputReqList(), WorkflowNodeInput.class));
            }
            // 节点输出
            if (workflowNodeReq.getWorkflowNodeOutputReqList().size() > 0) {
                workflowNodeDto.setWorkflowNodeOutputList(BeanUtil.copyToList(
                        workflowNodeReq.getWorkflowNodeOutputReqList(), WorkflowNodeOutput.class));
            }
            // 节点算法代码
            if (Objects.nonNull(workflowNodeReq.getWorkflowNodeCodeReq())) {
                workflowNodeDto.setWorkflowNodeCode(BeanUtil.toBean(
                        workflowNodeReq.getWorkflowNodeCodeReq(), WorkflowNodeCode.class));
            }
            // 节点环境资源
            if (Objects.nonNull(workflowNodeReq.getWorkflowNodeResourceReq())) {
                workflowNodeDto.setWorkflowNodeResource(BeanUtil.toBean(
                        workflowNodeReq.getWorkflowNodeResourceReq(), WorkflowNodeResource.class));
            }
            // 节点输入变量
            if (workflowNodeReq.getWorkflowNodeVariableReqList().size() > 0) {
                workflowNodeDto.setWorkflowNodeVariableList(BeanUtil.copyToList(
                        workflowNodeReq.getWorkflowNodeVariableReqList(), WorkflowNodeVariable.class));
            }
            // 工作流id
            workflowNodeDto.setWorkflowId(workflowNodeReq.getWorkflowId());
            // 节点算法id
            workflowNodeDto.setAlgorithmId(workflowNodeReq.getAlgorithmId());
            // 节点名称
            workflowNodeDto.setNodeName(workflowNodeReq.getNodeName());
            // 节点步骤
            workflowNodeDto.setNodeStep(workflowNodeReq.getNodeStep());
        });
        return workflowNodeDtoList;
    }

}
