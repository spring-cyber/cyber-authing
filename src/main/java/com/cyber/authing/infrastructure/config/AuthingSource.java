package com.cyber.authing.infrastructure.config;

import com.cyber.authing.domain.entity.AuthingSetting;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix="authing")
public class AuthingSource {

    List<AuthingSetting> sources;

    public List<AuthingSetting> getSources() {
        return sources;
    }

    public void setSources(List<AuthingSetting> sources) {
        this.sources = sources;
    }
}
