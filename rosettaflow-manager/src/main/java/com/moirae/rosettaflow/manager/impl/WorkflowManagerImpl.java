package com.moirae.rosettaflow.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moirae.rosettaflow.manager.WorkflowManager;
import com.moirae.rosettaflow.mapper.WorkflowMapper;
import com.moirae.rosettaflow.mapper.domain.Workflow;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public IPage<Workflow> getWorkflowList(Page<Workflow> page, String address, String keyword, Long algorithmId, Date begin, Date end) {
        return baseMapper.getWorkflowList(page, address, keyword, algorithmId, begin, end);
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
}
