package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.SysConfig;

@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {

}