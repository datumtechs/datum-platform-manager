package com.datum.platform.req.data;

import com.datum.platform.common.enums.DataOrderByEnum;
import com.datum.platform.mapper.enums.MetaDataFileTypeEnum;
import com.datum.platform.req.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "任务列表查询")
public class GetDataListReq extends CommonPageReq {

    @ApiModelProperty(value = "搜索关键字(凭证名称或元数据名称关键字)")
    private String keyword;

    @ApiModelProperty(value = "排序字段")
    private DataOrderByEnum orderBy;

    @ApiModelProperty(value = "行业名称")
    private String industry;

    @ApiModelProperty(value = "文件后缀/类型, 0:未知; 1:csv")
    private MetaDataFileTypeEnum fileType;

    @ApiModelProperty(value = "数据大小的最小值")
    private Long minSize;

    @ApiModelProperty(value = "数据大小的最大值")
    private Long maxSize;
}
