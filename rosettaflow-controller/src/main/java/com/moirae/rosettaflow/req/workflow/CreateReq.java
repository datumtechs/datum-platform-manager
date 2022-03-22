package com.moirae.rosettaflow.req.workflow;

import com.moirae.rosettaflow.common.enums.WorkflowCreateModeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author hudenian
 * @date 2021/8/27
 * @description 添加工作流请求对象
 */
@Data
@ApiModel
public class CreateReq {

    @ApiModelProperty(value = "创建模式", required = true)
    private WorkflowCreateModeEnum createMode;

    @ApiModelProperty(value = "工作流名称", required = true)
    @NotBlank(message = "{workflow.id.notNull}")
    @Length(max = 30, message = "{workflow.name.Length}")
    private String workflowName;

    @ApiModelProperty(value = "工作流描述（向导模式）")
    @Length(max = 200, message = "{workflow.desc.Length}")
    private String workflowDesc;

    @ApiModelProperty(value = "算法ID（向导模式）")
    private Long algorithmId;

    @ApiModelProperty(value = "计算流程id")
    private Integer calculationProcessId;
}
