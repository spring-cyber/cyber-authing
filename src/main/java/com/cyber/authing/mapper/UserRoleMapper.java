package com.cyber.authing.mapper;

import com.cyber.authing.entity.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapperPlus<UserRole> {
    List<String> selectPermsByUserId(String userId);
}