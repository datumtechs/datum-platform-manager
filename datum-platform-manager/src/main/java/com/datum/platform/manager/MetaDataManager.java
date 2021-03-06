package com.datum.platform.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.datum.platform.mapper.domain.MetaData;

import java.util.List;

public interface MetaDataManager extends IService<MetaData> {

    IPage<MetaData> getDataListByOrg(Page<MetaData> page, String identityId);

    IPage<MetaData> getDataList(Page<MetaData> page, String keyword, String industry, Integer fileType, Long minSize, Long maxSize, String orderBy);

    MetaData getDataDetails(String metaDataId);

    IPage<MetaData> getUserDataList(Page<MetaData> page, String address, String identityId, String keyword);

    MetaData statisticsOfGlobal();

    List<MetaData> listDataByTokenAddress(String tokenAddress);

    List<String> listDataOrgIdByUser(String address);
}
