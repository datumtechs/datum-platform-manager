package com.platon.rosettaflow.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 项目模板列表返回参数
 * @author houz
 */
@Data
@ApiModel(value = "项目模板列表返回参数")
public class ProjTempListVo {

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目描述")
    private String projectDesc;

}
