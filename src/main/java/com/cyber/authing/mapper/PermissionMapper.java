package com.cyber.authing.mapper;

import com.cyber.authing.entity.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectByIds(List<Long> ids);
}