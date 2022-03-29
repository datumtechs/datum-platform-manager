package com.moirae.rosettaflow.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moirae.rosettaflow.manager.AlgorithmClassifyManager;
import com.moirae.rosettaflow.mapper.AlgorithmClassifyMapper;
import com.moirae.rosettaflow.mapper.domain.AlgorithmClassify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlgorithmClassifyManagerImpl extends ServiceImpl<AlgorithmClassifyMapper, AlgorithmClassify> implements AlgorithmClassifyManager  {
}