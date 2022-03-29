package com.moirae.rosettaflow.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moirae.rosettaflow.mapper.domain.MetaData;
import com.moirae.rosettaflow.req.CommonPageReq;
import com.moirae.rosettaflow.req.data.GetDataDetailsReq;
import com.moirae.rosettaflow.req.data.GetDataListByIdentityIdReq;
import com.moirae.rosettaflow.req.data.GetDataListReq;
import com.moirae.rosettaflow.service.DataService;
import com.moirae.rosettaflow.utils.ConvertUtils;
import com.moirae.rosettaflow.vo.PageVo;
import com.moirae.rosettaflow.vo.ResponseVo;
import com.moirae.rosettaflow.vo.data.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hudenian
 * @date 2021/8/25
 * @description 数据管理
 */
@Slf4j
@RestController
@Api(tags = "数据管理关接口")
@RequestMapping(value = "data", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataController {

    @Resource
    private DataService dataService;

    @GetMapping("getDataStats")
    @ApiOperation(value = "查询数据统计", notes = "查询数据统计")
    public ResponseVo<DataStatsVo> getDataStats() {
        int dataCount = dataService.getDataCount();
        DataStatsVo dataStatsVo = new DataStatsVo();
        dataStatsVo.setDataCount(dataCount);
        return ResponseVo.createSuccess(dataStatsVo);
    }

    @GetMapping("getDataListByIdentityId")
    @ApiOperation(value = "查询数据列表通过组织id", notes = "查询数据列表通过组织id")
    public ResponseVo<PageVo<DataVo>> getDataListByIdentityId(@Valid GetDataListByIdentityIdReq req) {
        IPage<MetaData> page = dataService.getDataListByIdentityId(req.getCurrent(), req.getSize(), req.getIdentityId());
        List<DataVo> itemList = BeanUtil.copyToList(page.getRecords(), DataVo.class);
        return ResponseVo.createSuccess(ConvertUtils.convertPageVo(page, itemList));
    }

    @GetMapping("getDataList")
    @ApiOperation(value = "查询数据列表", notes = "查询数据列表")
    public ResponseVo<PageVo<DataVo>> getDataList(@Valid GetDataListReq req) {
        IPage<MetaData> page = dataService.getDataList(req.getCurrent(), req.getSize(), req.getKeyword(), req.getIndustry(), req.getFileType(), req.getMinSize(), req.getMaxSize(), req.getOrderBy());
        List<DataVo> itemList = BeanUtil.copyToList(page.getRecords(), DataVo.class);
        return ResponseVo.createSuccess(ConvertUtils.convertPageVo(page, itemList));
    }

    @GetMapping("getDataDetails")
    @ApiOperation(value = "查询数据详情", notes = "查询数据详情")
    public ResponseVo<DataDetailsVo> getDataDetails(@Valid GetDataDetailsReq req) {
        MetaData data = dataService.getDataDetails(req.getMetaDataId());
        return ResponseVo.createSuccess(BeanUtil.copyProperties(data, DataDetailsVo.class));
    }

    @GetMapping("getUserDataList")
    @ApiOperation(value = "查询用户的数据列表", notes = "查询用户的数据列表(存在余额的)")
    public ResponseVo<PageVo<UserDataVo>> getUserDataList(@Valid CommonPageReq req) {
        IPage<MetaData> page = dataService.getUserDataList(req.getCurrent(), req.getSize());
        List<UserDataVo> itemList = BeanUtil.copyToList(page.getRecords(), UserDataVo.class);
        return ResponseVo.createSuccess(ConvertUtils.convertPageVo(page, itemList));
    }
}
