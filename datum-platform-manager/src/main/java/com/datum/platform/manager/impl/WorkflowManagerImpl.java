package com.datum.platform.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datum.platform.common.enums.ErrorMsg;
import com.datum.platform.common.enums.RespCodeEnum;
import com.datum.platform.common.exception.BusinessException;
import com.datum.platform.manager.WorkflowManager;
import com.datum.platform.mapper.WorkflowMapper;
import com.datum.platform.mapper.domain.Workflow;
import com.datum.platform.mapper.enums.WorkflowCreateModeEnum;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 工作流表 服务实现类
 * </p>
 *
 * @author chendai
 * @since 2022-03-30
 */
@Service
public class WorkflowManagerImpl extends ServiceImpl<WorkflowMapper, Workflow> implements WorkflowManager {

    @Override
    public int getWorkflowCount(String address) {
        LambdaQueryWrapper<Workflow> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Workflow::getAddress, address);
        return count(wrapper);
    }

    @Override
    public IPage<Workflow> getWorkflowList(Page<Workflow> page, String address, String keyword, Long algorithmId, Date begin, Date end, Integer createMode) {
        return baseMapper.getWorkflowList(page, address, keyword, algorithmId, begin, end, createMode);
    }

    @Override
    public boolean updateStep(Long workflowId, Integer step, Boolean isSettingCompleted) {
        LambdaUpdateWrapper<Workflow> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(Workflow::getCalculationProcessStep, step);
        wrapper.set(Workflow::getIsSettingCompleted, isSettingCompleted);
        wrapper.eq(Workflow::getWorkflowId, workflowId);
        return update(wrapper);
    }

    @Override
    public Workflow increaseVersion(Long workflowId) {
        Workflow workflow = getById(workflowId);
        workflow.setWorkflowVersion(workflow.getWorkflowVersion() + 1);
        updateById(workflow);
        return workflow;
    }

    @Override
    public Workflow delete(Long workflowId) {
        Workflow workflow = getById(workflowId);
        removeById(workflow.getWorkflowId());
        return workflow;
    }

    @Override
    public Workflow createOfWizardMode(String workflowName, String workflowDesc, Long algorithmId, String algorithmName, Long calculationProcessId, String calculationProcessName, String address) {
        Workflow workflow = new Workflow();
        workflow.setCreateMode(WorkflowCreateModeEnum.WIZARD_MODE);
        workflow.setWorkflowName(workflowName);
        workflow.setWorkflowDesc(workflowDesc);
        workflow.setAlgorithmId(algorithmId);
        workflow.setAlgorithmName(algorithmName);
        workflow.setCalculationProcessId(calculationProcessId);
        workflow.setCalculationProcessName(calculationProcessName);
        workflow.setCalculationProcessStep(0);
        workflow.setWorkflowVersion(1L);
        workflow.setAddress(address);
        return create(workflow);
    }

    @Override
    public Workflow createOfExpertMode(String workflowName, String address) {
        Workflow workflow = new Workflow();
        workflow.setCreateMode(WorkflowCreateModeEnum.EXPERT_MODE);
        workflow.setWorkflowName(workflowName);
        workflow.setWorkflowVersion(1L);
        workflow.setAddress(address);
        return create(workflow);
    }

    private Workflow create(Workflow workflow){
        try{
            if(save(workflow)){
                return workflow;
            }else {
                return null;
            }
        }catch (DuplicateKeyException e) {
            throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.WORKFLOW_NAME_EXIST.getMsg(),e);
        }
    }

    @Override
    public void updateLastRunTime(Long workflowId) {
        this.baseMapper.updateLastRunTime(workflowId);
    }

    @Override
    public List<Workflow> listByNameAndAddress(String address, String workflowName) {
        LambdaQueryWrapper<Workflow> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Workflow::getAddress, address);
        wrapper.eq(Workflow::getWorkflowName, workflowName);
        return list(wrapper);
    }
}
