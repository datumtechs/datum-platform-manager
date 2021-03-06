package com.datum.platform.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datum.platform.mapper.domain.TaskDataProvider;

import java.util.List;

public interface TaskDataProviderManager extends IService<TaskDataProvider> {

    List<TaskDataProvider> listByTaskId(String taskId);

    Long countOfTokenUsed(List<String> metaDataIdList);
}
