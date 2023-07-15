package com.cyber.authing.domain.repository;

import com.cyber.authing.domain.entity.RoleMenu;
import com.cyber.infrastructure.repository.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    Integer deleteByRoleId(long roleId);

    Integer saveBatch(List<RoleMenu> roleMenus);

    List<RoleMenu> selectList(RoleMenu roleMenu);
}
