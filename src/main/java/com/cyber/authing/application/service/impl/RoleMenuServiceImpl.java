package com.cyber.authing.application.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cyber.authing.application.service.RoleMenuService;
import com.cyber.authing.domain.entity.RoleMenu;
import com.cyber.authing.domain.repository.RoleMenuMapper;
import com.cyber.domain.entity.PagingData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleMenuServiceImpl implements RoleMenuService {

    private final RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional
    public Integer save(RoleMenu roleMenu) {

        if( null == roleMenu ) {
            log.warn("save roleMenu, but roleMenu is null...");
            return 0;
        }

        roleMenu.setId(IdUtil.simpleUUID());
        return roleMenuMapper.save( roleMenu );
    }

    @Override
    @Transactional
    public Integer deleteById(RoleMenu roleMenu) {

        if( null == roleMenu ) {
            log.warn("delete roleMenu, but roleMenu is null  or roleMenu id is null...");
            return 0;
        }

        return roleMenuMapper.deleteById( roleMenu );
    }

    @Override
    @Transactional
    public Integer updateById(RoleMenu roleMenu) {

        if( null == roleMenu ) {
            log.warn("update roleMenu, but roleMenu is null  or roleMenu id is null...");
            return 0;
        }

        return roleMenuMapper.updateById( roleMenu );
    }

    @Override
    public RoleMenu selectOne(RoleMenu roleMenu) {
        if( null == roleMenu ) {
            log.warn("select roleMenu one, but roleMenu is null ...");
            return null;
        }
        roleMenu = roleMenuMapper.selectOne( roleMenu );
        return roleMenu;
    }


    @Override
    public PagingData<RoleMenu> selectPage(RoleMenu roleMenu) {
        PagingData<RoleMenu> PagingData = new PagingData<>();

        if( null == roleMenu ) {
            log.warn("select roleMenu page, but roleMenu is null...");
            return PagingData;
        }

        Integer queryCount = roleMenuMapper.selectByIndexCount( roleMenu );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select roleMenu page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<RoleMenu> roleMenus =  selectByIndex( roleMenu );
        PagingData.setData( roleMenus );
        return PagingData;
    }

    @Override
    public List<RoleMenu> selectByIndex(RoleMenu roleMenu) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        if( null == roleMenu ) {
            log.warn("select roleMenu by index, but roleMenu is null ...");
            return roleMenus;
        }

        roleMenus = roleMenuMapper.selectByIndex( roleMenu );

        return roleMenus;
    }

    @Override
    public Integer deleteByRoleId(long roleId) {
        return roleMenuMapper.deleteByRoleId(roleId);
    }

    @Override
    public Integer saveBatch(List<RoleMenu> roleMenus) {
        return roleMenuMapper.saveBatch(roleMenus);
    }

    @Override
    public List<RoleMenu> selectList(RoleMenu roleMenu) {
        return roleMenuMapper.selectList(roleMenu);
    }
}
