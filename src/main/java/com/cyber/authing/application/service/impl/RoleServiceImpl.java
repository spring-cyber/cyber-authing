package com.cyber.authing.application.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cyber.authing.application.service.RoleMenuService;
import com.cyber.authing.application.service.RoleService;
import com.cyber.authing.domain.entity.Role;
import com.cyber.authing.domain.entity.RoleMenu;
import com.cyber.authing.domain.repository.RoleMapper;
import com.cyber.domain.entity.PagingData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleMenuService roleMenuService;

    @Override
    @Transactional
    public Integer save(Role role) {

        if( null == role ) {
            log.warn("save role, but role is null...");
            return 0;
        }

        role.setId(IdUtil.simpleUUID());

        saveRoleMenus(role);
        return roleMapper.save( role );
    }

    private void saveRoleMenus(Role role) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        if (null != role.getMenuIds()) {
            roleMenus = role.getMenuIds().stream().map(menuId->{
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(role.getId());
                roleMenu.setMenuId(menuId);
                return roleMenu;
            }).collect(Collectors.toList());

        }
        if (!roleMenus.isEmpty()) {
            roleMenuService.deleteByRoleId(Long.parseLong(role.getId()));
            roleMenuService.saveBatch(roleMenus);
        }

    }

    @Override
    @Transactional
    public Integer deleteById(Role role) {

        if( null == role ) {
            log.warn("delete role, but role is null  or role id is null...");
            return 0;
        }
        roleMenuService.deleteByRoleId(Long.parseLong(role.getId()));
        return roleMapper.deleteById( role );
    }

    @Override
    @Transactional
    public Integer updateById(Role role) {

        if( null == role ) {
            log.warn("update role, but role is null  or role id is null...");
            return 0;
        }
        saveRoleMenus(role);
        return roleMapper.updateById( role );
    }

    @Override
    public Role selectOne(Role role) {
        if( null == role ) {
            log.warn("select role one, but role is null ...");
            return null;
        }
        role = roleMapper.selectOne( role );
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(role.getId());
        List<RoleMenu> roleMenus =roleMenuService.selectList(roleMenu);
        if (!roleMenus.isEmpty()) {
            role.setMenuIds(roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList()));
        }
        return role;
    }


    @Override
    public PagingData<Role> selectPage(Role role) {
        PagingData<Role> PagingData = new PagingData<>();

        if( null == role ) {
            log.warn("select role page, but role is null...");
            return PagingData;
        }

        Integer queryCount = roleMapper.selectByIndexCount( role );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select role page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<Role> roles =  selectByIndex( role );
        PagingData.setData( roles );
        return PagingData;
    }

    @Override
    public List<Role> selectByIndex(Role role) {
        List<Role> roles = new ArrayList<>();
        if( null == role ) {
            log.warn("select role by index, but role is null ...");
            return roles;
        }

        roles = roleMapper.selectByIndex( role );

        return roles;
    }

    @Override
    public List<Role> selectList(Role role) {
        List<Role> roles = new ArrayList<>();
        if( null == role ) {
            log.warn("select role by list, but role is null ...");
            return roles;
        }

        roles = roleMapper.selectList( role );

        return roles;
    }
}
