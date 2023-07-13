package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.UserRole;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}