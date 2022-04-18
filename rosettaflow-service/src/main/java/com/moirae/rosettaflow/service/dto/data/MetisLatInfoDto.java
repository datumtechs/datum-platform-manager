package com.moirae.rosettaflow.service.dto.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "MetisLat账户信息")
public class MetisLatInfoDto {

    @ApiModelProperty(value = "数据凭证名称")
    private String tokenName;

    @ApiModelProperty(value = "数据凭证符号")
    private String tokenSymbol;

    @ApiModelProperty(value = "数据凭证精度")
    private Long tokenDecimal;

    @ApiModelProperty(value = "数据凭证余额")
    private String tokenBalance;

    @ApiModelProperty(value = "已授权给支付合约的金额")
    private String authorizeBalance;
}
