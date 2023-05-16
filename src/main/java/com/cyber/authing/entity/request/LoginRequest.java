package com.cyber.authing.entity.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/8
 */
@Data
public class LoginRequest {
    /**
     * 用户名称
     */
    @NotBlank(message = "用户名不能为空")
    private String name;
    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 图形验证码
     */
    private String validCode;

    /**
     * 短信验证码
     */
    private String smsValidCode;
}
