package com.moirae.rosettaflow.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moirae.rosettaflow.manager.ModelManager;
import com.moirae.rosettaflow.mapper.ModelMapper;
import com.moirae.rosettaflow.mapper.domain.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ModelManagerImpl extends ServiceImpl<ModelMapper, Model> implements ModelManager {
    @Override
    public List<Model> queryAvailableModel(String address, Long algorithmId, String identityId) {
        return baseMapper.queryAvailableModel(address, algorithmId, identityId);
    }

    @Override
    public List<Model> getLatestModel(Integer size) {
        return baseMapper.getLatestModel(size);
    }
}