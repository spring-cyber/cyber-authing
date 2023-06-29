package com.cyber.authing.service;

import com.cyber.authing.entity.domain.Permission;

import java.util.List;

public interface PermissionService extends BaseService<Permission> {
    List<Permission> selectByIds(List<Long> ids);
}
