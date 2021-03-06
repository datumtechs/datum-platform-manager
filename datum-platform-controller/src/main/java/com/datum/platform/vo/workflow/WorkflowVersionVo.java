package com.datum.platform.vo.workflow;

import com.datum.platform.mapper.enums.WorkflowCreateModeEnum;
import com.datum.platform.mapper.enums.WorkflowTaskRunStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "工作流版本列表响应对象")
public class WorkflowVersionVo {

    @ApiModelProperty(value = "工作流ID")
    private Long workflowId;

    @ApiModelProperty(value = "工作流版本号")
    private Integer workflowVersion;

    @ApiModelProperty(value = "工作流版本名称")
    private String workflowVersionName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "工作流运行记录id")
    private Long workflowRunId;

    @ApiModelProperty(value = "创建模式（专家模型、向导模式）")
    private WorkflowCreateModeEnum createMode;

    @ApiModelProperty(value = "工作流版本执行状态（0-待运行、1-运行中、2-运行成功、3-运行失败）")
    private WorkflowTaskRunStatusEnum status;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
