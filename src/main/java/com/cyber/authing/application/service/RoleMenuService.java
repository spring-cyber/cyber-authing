package com.cyber.authing.application.service;

import com.cyber.application.service.BaseService;
import com.cyber.authing.domain.entity.RoleMenu;

import java.util.List;

public interface RoleMenuService extends BaseService<RoleMenu> {


    Integer deleteByRoleId(long roleId);

    Integer saveBatch(List<RoleMenu> roleMenus);

    List<RoleMenu> selectList(RoleMenu roleMenu);
}

