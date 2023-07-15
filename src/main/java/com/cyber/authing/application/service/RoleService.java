package com.cyber.authing.application.service;

import com.cyber.application.service.BaseService;
import com.cyber.authing.domain.entity.Role;

import java.util.List;

public interface RoleService extends BaseService<Role> {


    List<Role> selectList(Role role);
}
