package com.datum.platform.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datum.platform.mapper.domain.TaskEvent;

import java.util.List;

public interface TaskEventManager extends IService<TaskEvent> {
    List<TaskEvent> listByTaskId(String taskId);
}
