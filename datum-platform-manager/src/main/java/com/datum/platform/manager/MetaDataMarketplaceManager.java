package com.datum.platform.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datum.platform.mapper.domain.MetaDataMarketplace;

import java.util.List;

/**
 * <p>
 * 数据市场可见的元数据 服务类
 * </p>
 *
 * @author chendai
 * @since 2022-06-28
 */
public interface MetaDataMarketplaceManager extends IService<MetaDataMarketplace> {

    boolean batchReplace(List<MetaDataMarketplace> metaDataMarketplaceList);
}
