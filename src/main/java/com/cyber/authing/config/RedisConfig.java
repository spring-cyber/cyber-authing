package com.cyber.authing.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Autowired
    Environment environment;

    @Bean
    @ConditionalOnBean(RedisStandaloneConfiguration.class)
    public RedisTemplate<String, Object> redisTemplate() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration());
        jedisConnectionFactory.afterPropertiesSet();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        String host = environment.getProperty("redis.host");
        Integer port = environment.getProperty("redis.port", Integer.class);
        String password = environment.getProperty("redis.password");
        // host和port为空不初始化
        if (StringUtils.isNotBlank(host) && null != port) {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
            if (StringUtils.isNotBlank(password)) {
                config.setPassword(password);
            }
            return config;
        }
        return null;
    }
}
