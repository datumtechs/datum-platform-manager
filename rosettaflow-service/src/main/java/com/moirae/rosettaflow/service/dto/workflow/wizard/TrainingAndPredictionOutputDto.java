package com.moirae.rosettaflow.service.dto.workflow.wizard;

import com.moirae.rosettaflow.service.dto.workflow.common.OutputDto;
import com.moirae.rosettaflow.service.dto.workflow.common.ResourceDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加工作流节点资源请求对象
 *
 * @author hudenian
 * @date 2021/9/28
 */
@Data
@ApiModel(value = "工作流节点资源请求对象")
public class TrainingAndPredictionOutputDto {

    @ApiModelProperty(value = "训练任务的计算资源")
    private OutputDto training;

    @ApiModelProperty(value = "预测任务的计算资源")
    private OutputDto prediction;
}