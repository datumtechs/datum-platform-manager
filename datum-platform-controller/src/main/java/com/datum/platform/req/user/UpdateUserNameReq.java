package com.datum.platform.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author admin
 * @date 2021/8/17
 */
@Data
@ApiModel("修改昵称请求参数")
public class UpdateUserNameReq {

    @ApiModelProperty(value = "昵称", required = true, example = "用户1")
    @NotBlank(message = "{user.nickname.notBlank}")
    private String userName;
}
