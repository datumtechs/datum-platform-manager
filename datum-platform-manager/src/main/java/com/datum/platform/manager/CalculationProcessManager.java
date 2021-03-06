package com.datum.platform.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datum.platform.mapper.domain.CalculationProcess;

import java.util.List;

public interface CalculationProcessManager extends IService<CalculationProcess> {
    List<CalculationProcess> getCalculationProcessList(Long algorithmId);
}
