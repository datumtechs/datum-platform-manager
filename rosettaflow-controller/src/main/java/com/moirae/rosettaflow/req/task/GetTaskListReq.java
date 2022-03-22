package com.moirae.rosettaflow.req.task;

import com.moirae.rosettaflow.common.enums.TaskStatusEnum;
import com.moirae.rosettaflow.req.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "任务列表查询")
public class GetTaskListReq extends CommonPageReq {

    @ApiModelProperty(value = "搜索关键字(任务id)")
    private String keyword;

    @ApiModelProperty(value = "算法id")
    private String algorithmId;

    @ApiModelProperty(value = "时间的开始")
    private Date begin;

    @ApiModelProperty(value = "时间的结束")
    private Date end;

    @ApiModelProperty(value = "任务状态")
    private TaskStatusEnum taskStatus;

    @ApiModelProperty(value = "组织关联的（用于组织详情查询）")
    private String identityId;

    @ApiModelProperty(value = "元文件关联的（用于数据详情查询）")
    private String metaDataId;

    @ApiModelProperty(value = "工作流ID（用于工作流子任务列表查询）")
    private Long workflowId;

    @ApiModelProperty(value = "工作流版本（用于工作流子任务列表查询）")
    private Long workflowVersion;
}
