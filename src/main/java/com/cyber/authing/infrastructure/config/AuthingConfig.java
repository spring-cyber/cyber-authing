package com.cyber.authing.infrastructure.config;

import com.cyber.authing.domain.entity.AuthingSetting;
import com.cyber.authing.domain.request.AuthingRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class AuthingConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthingConfig.class);

    @Autowired
    AuthingSource authingSource;

    AuthingRequest

    @PostConstruct
    public void build() {
       if(!CollectionUtils.isEmpty(authingSource.sources)) {
           for(AuthingSetting setting : authingSource.sources) {
               setting.
           }
       }
    }

}
