package com.datum.platform.controller;

import cn.hutool.core.bean.BeanUtil;
import com.datum.platform.mapper.domain.AlgorithmClassify;
import com.datum.platform.service.AlgService;
import com.datum.platform.service.dto.alg.AlgTreeDto;
import com.datum.platform.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "算法相关接口")
@RequestMapping(value = "alg", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlgController {

    @Resource
    private AlgService algService;

    @GetMapping("getAlgTree")
    @ApiOperation(value = "查询算法树", notes = "查询算法树")
    public ResponseVo<AlgTreeDto> getAlgTree() {
        AlgorithmClassify algorithmClassify = algService.getAlgorithmClassifyTree(false);
        return ResponseVo.createSuccess(BeanUtil.copyProperties(algorithmClassify, AlgTreeDto.class));
    }

    @GetMapping("getAlgTreeDetails")
    @ApiOperation(value = "查询算法树详情", notes = "查询算法树详情（包含算法代码及变量设置）")
    public ResponseVo<AlgTreeDto> getAlgTreeDetails() {
        AlgorithmClassify algorithmClassify = algService.getAlgorithmClassifyTree(true);
        return ResponseVo.createSuccess(BeanUtil.copyProperties(algorithmClassify, AlgTreeDto.class));
    }
}
