package com.cyber.authing.mapper;

import com.cyber.authing.entity.domain.RolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    Integer batchSave(List<RolePermission> rolePermissionList);
}