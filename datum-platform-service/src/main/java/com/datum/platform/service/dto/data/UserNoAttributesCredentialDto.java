package com.datum.platform.service.dto.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户无属性凭证账户信息")
public class UserNoAttributesCredentialDto extends HaveAttributesCredentialDto {

    @ApiModelProperty(value = "账户余额")
    private String balance;

    @ApiModelProperty(value = "已授权金额")
    private String authorizeBalance;
}
