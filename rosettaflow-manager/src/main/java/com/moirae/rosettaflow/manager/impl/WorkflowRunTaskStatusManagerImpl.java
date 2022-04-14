package com.moirae.rosettaflow.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.moirae.rosettaflow.grpc.task.req.dto.TaskEventDto;
import com.moirae.rosettaflow.mapper.domain.TaskEvent;
import com.moirae.rosettaflow.mapper.domain.WorkflowRunTaskStatus;
import com.moirae.rosettaflow.mapper.WorkflowRunTaskStatusMapper;
import com.moirae.rosettaflow.manager.WorkflowRunTaskStatusManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 工作流任务运行状态 服务实现类
 * </p>
 *
 * @author chendai
 * @since 2022-03-30
 */
@Service
public class WorkflowRunTaskStatusManagerImpl extends ServiceImpl<WorkflowRunTaskStatusMapper, WorkflowRunTaskStatus> implements WorkflowRunTaskStatusManager {

    @Override
    public WorkflowRunTaskStatus getByWorkflowRunIdAndStep(Long workflowRunId, Integer taskStep) {
        LambdaQueryWrapper<WorkflowRunTaskStatus> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkflowRunTaskStatus::getWorkflowTaskId, workflowRunId);
        wrapper.eq(WorkflowRunTaskStatus::getStep, taskStep);
        return getOne(wrapper);
    }

    @Override
    public List<WorkflowRunTaskStatus> listByWorkflowRunIdAndHasTaskId(Long workflowRunId) {
        LambdaQueryWrapper<WorkflowRunTaskStatus> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkflowRunTaskStatus::getWorkflowTaskId, workflowRunId);
        wrapper.isNotNull(WorkflowRunTaskStatus::getTaskId);
        wrapper.orderByAsc(WorkflowRunTaskStatus::getStep);
        return list(wrapper);
    }
}