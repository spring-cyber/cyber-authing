package com.cyber.authing.service;

import com.cyber.authing.entity.domain.UserRole;

import java.util.List;

public interface UserRoleService extends BaseService<UserRole> {

    List<String> selectPermsByUserId(String userId);
}
