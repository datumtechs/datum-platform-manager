package com.datum.platform.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datum.platform.mapper.domain.MetaDataColumn;

import java.util.List;

public interface MetaDataColumnManager extends IService<MetaDataColumn> {

    List<MetaDataColumn> getList(String metaDataId);

    MetaDataColumn getById(String metaDataId, int columnIndex);

    List<MetaDataColumn> listByIdAndIndex(String metaDataId, List<Integer> selectedColumnsV2);
}
