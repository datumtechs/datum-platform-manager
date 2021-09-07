package com.platon.rosettaflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platon.rosettaflow.dto.MetaDataDto;
import com.platon.rosettaflow.mapper.domain.MetaData;

import java.util.List;

/**
 * @author admin
 */
public interface MetaDataMapper extends BaseMapper<MetaData> {
    /**
     * 清空表
     */
    void truncate();

    /**
     * 根据钱包地址查询元数据
     * @param address 钱包地址
     * @return 钱包地址查询到授权数据
     */
    List<MetaDataDto> selectMetaDataWithAuth(String address);
}