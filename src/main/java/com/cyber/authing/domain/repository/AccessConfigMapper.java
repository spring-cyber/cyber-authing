package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.AccessConfig;

@Mapper
public interface AccessConfigMapper extends BaseMapper<AccessConfig> {

}