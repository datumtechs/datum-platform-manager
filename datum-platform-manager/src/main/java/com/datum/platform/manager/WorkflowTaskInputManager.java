package com.datum.platform.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datum.platform.common.enums.OldAndNewEnum;
import com.datum.platform.mapper.domain.WorkflowTaskInput;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 工作流任务输入表 服务类
 * </p>
 *
 * @author chendai
 * @since 2022-03-30
 */
public interface WorkflowTaskInputManager extends IService<WorkflowTaskInput> {

    List<WorkflowTaskInput> listByWorkflowTaskId(Long workflowTaskId);

    boolean removeByWorkflowTaskIds(List<Long> workflowTaskIdList);

    List<Map<OldAndNewEnum, WorkflowTaskInput>> copy(Long oldWorkflowTaskId, Long newWorkflowTaskId);

    List<WorkflowTaskInput> deleteByWorkflowTaskId(Long workflowTaskId);

    boolean setWorkflowTaskInput(Optional<Long> prePsiWorkflowTaskId, Long workflowTaskId, List<WorkflowTaskInput> workflowTaskInputList);
}
