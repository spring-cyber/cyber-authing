package com.cyber.authing.infrastructure.service;

import cn.hutool.core.collection.CollUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ResourceServiceImpl {

    private final RedisTemplate<String, Object> redisTemplate;

    public ResourceServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void initData() {
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/app-api/publish", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/app-api/get-page-list", CollUtil.toList("ADMIN", "USER"));
        redisTemplate.opsForHash().putAll("RESOURCE_ROLES_MAP", resourceRolesMap);
    }
}
