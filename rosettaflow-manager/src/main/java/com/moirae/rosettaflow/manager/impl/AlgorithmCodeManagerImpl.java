package com.moirae.rosettaflow.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moirae.rosettaflow.manager.AlgorithmCodeManager;
import com.moirae.rosettaflow.mapper.AlgorithmCodeMapper;
import com.moirae.rosettaflow.mapper.domain.AlgorithmCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlgorithmCodeManagerImpl extends ServiceImpl<AlgorithmCodeMapper, AlgorithmCode> implements AlgorithmCodeManager {
}