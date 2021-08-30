package com.platon.rosettaflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platon.rosettaflow.mapper.domain.ProjectTemp;

import java.util.List;


/**
 * @author houz
 */
public interface ProjectTempMapper extends BaseMapper<ProjectTemp> {

    /**
     * 查询项目模板列表
     * @return
     */
    List<ProjectTemp> queryProjectTempList();
}