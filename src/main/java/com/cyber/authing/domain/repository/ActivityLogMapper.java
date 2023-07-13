package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.ActivityLog;

@Mapper
public interface ActivityLogMapper extends BaseMapper<ActivityLog> {

}