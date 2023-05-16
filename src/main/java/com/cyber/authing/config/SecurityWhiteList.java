package com.cyber.authing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/8
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "security")
public class SecurityWhiteList {
    private String[] whiteList = new String[0];
}
