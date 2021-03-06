package com.datum.platform.mapper.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "mo_stats_org")
public class StatsOrg implements Serializable {

    /**
     * 身份认证标识的id
     */
    @TableId
    private String identityId;

    /**
     * 全网目前的算力核心数，单位：个
     */
    private int orgTotalCore;

    /**
     * 全网目前的算力内存数，单位：字节
     */
    private long orgTotalMemory;

    /**
     * 全网目前的带宽，单位：字节
     */
    private long orgTotalBandwidth;

    /**
     * 算力占比. 万分比。100 = 1%
     */
    private Integer computingPowerRatio;

    /**
     * 参与任务数量
     */
    private long totalTask;

    /**
     * 凭证数
     */
    private long totalDataToken;

    /**
     * 数据总数
     */
    private long totalData;

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String nodeName;
}
