package com.cyber.authing.common.constant;

import java.util.Arrays;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/11
 */
public enum AuthConstant {
    PERMISSION_PREFIX("permission_prefix:"),
    UNKNOWN("unknown");

    private String key;

    AuthConstant(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public static AuthConstant getInstance(String key) {
        return Arrays.asList(AuthConstant.values()).stream().filter(constant -> constant.getKey().equals(key)).findFirst().orElse(AuthConstant.UNKNOWN);
    }
}
