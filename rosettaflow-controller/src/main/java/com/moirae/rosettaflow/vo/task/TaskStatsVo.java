package com.moirae.rosettaflow.vo.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moirae.rosettaflow.common.constants.SysConstant;
import com.moirae.rosettaflow.common.utils.LanguageContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Objects;

@Data
@ApiModel(value = "任务统计")
public class TaskStatsVo {

    @ApiModelProperty(value = "隐私计算总次数(总的任务数,包括成功和失败的)")
    private int taskCount;
}