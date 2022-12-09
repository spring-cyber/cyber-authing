package com.cyber.authing.infrastructure.enums;

import lombok.Getter;

/**
 * @description:
 * @author: snow4aa
 * @create: 2022-08-07 22:31
 */
public enum PasswordEncoderTypeEnum {

    BCRYPT("{bcrypt}","BCRYPT加密"),
    NOOP("{noop}","无加密明文");

    @Getter
    private String prefix;

    PasswordEncoderTypeEnum(String prefix, String desc){
        this.prefix=prefix;
    }
}
