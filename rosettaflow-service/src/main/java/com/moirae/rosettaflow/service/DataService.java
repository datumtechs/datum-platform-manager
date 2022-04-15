package com.moirae.rosettaflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moirae.rosettaflow.common.enums.DataOrderByEnum;
import com.moirae.rosettaflow.mapper.domain.*;
import com.moirae.rosettaflow.mapper.enums.MetaDataFileTypeEnum;
import com.moirae.rosettaflow.service.dto.data.MetisLatInfoDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataService {

    /**
     * 查询有效的数据总数
     *
     * @return
     */
    int getDataCount();

    /**
     * 查询数据列表
     *
     * @param current
     * @param size
     * @param identityId
     * @return
     */
    IPage<MetaData> getDataListByOrg(Long current, Long size, String identityId);

    /**
     * 查询数据列表
     *
     * @param current
     * @param size
     * @param keyword 凭证名称、元数据id
     * @param industry
     * @param fileType
     * @param minSize
     * @param maxSize
     * @param orderBy
     * @return
     */
    IPage<MetaData> getDataList(Long current, Long size, String keyword, String industry, MetaDataFileTypeEnum fileType, Long minSize, Long maxSize, DataOrderByEnum orderBy);

    /**
     * 查询数据详情
     *
     * @param metaDataId
     * @return
     */
    MetaData getDataDetails(String metaDataId);

    /**
     * 查询数据列表（用户存在余额的）
     *
     * @param current
     * @param size
     * @return
     */
    IPage<MetaData> getUserDataList(Long current, Long size);

    void batchReplace(List<MetaData> metaDataList, List<MetaDataColumn> metaDataColumnList, List<Token> tokenList);

    Map<String, MetaData> getMetaDataId2MetaDataMap(Set<String> metaDataId);

    MetaDataColumn getByKey(String metaDataId, Integer columnIdx);

    void checkMetaDataEffective(String metaDataId);

    void checkMetaDataAuthListEffective(String address, Set<String> metaDataIdList);

    List<Token> getNeedSyncedTokenList(int size);

    boolean updateToken(Token token);

    List<String> getTokenIdList();

    boolean batchInsertOrUpdateTokenHolder(String address, List<TokenHolder> tokenHolderList);

    List<Model> queryAvailableModel(Long algorithmId, String identityId);

    Model getModelById(String modelId);

    MetisLatInfoDto getUserMetisLatInfo();

    boolean saveToken(Token token);

    Token getTokenById(String weth);
}
