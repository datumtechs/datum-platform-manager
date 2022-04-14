package com.moirae.rosettaflow.service.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel("获取登录nonce参数")
public class NonceDto {
    @ApiModelProperty("登录随机数")
    private String nonce;
}