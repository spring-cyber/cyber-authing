package com.cyber.authing.mapper;

import com.cyber.authing.entity.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    List<String> selectPermsByUserId(String userId);

    Integer deleteByIds(List<Long> ids);

    Integer batchSave(List<UserRole> userRoleList);
}