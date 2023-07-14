package com.cyber.authing.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.UserRoleMapper;
import com.cyber.authing.domain.entity.UserRole;
import com.cyber.authing.application.service.UserRoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public Integer save(UserRole userRole) {

        if( null == userRole ) {
            log.warn("save userRole, but userRole is null...");
            return 0;
        }
        userRole.setId(String.valueOf(IdUtil.getSnowflakeNextId()));

        return userRoleMapper.save( userRole );
    }

    @Override
    @Transactional
    public Integer deleteById(UserRole userRole) {

        if( null == userRole ) {
            log.warn("delete userRole, but userRole is null  or userRole id is null...");
            return 0;
        }

        return userRoleMapper.deleteById( userRole );
    }

    @Override
    @Transactional
    public Integer updateById(UserRole userRole) {

        if( null == userRole ) {
            log.warn("update userRole, but userRole is null  or userRole id is null...");
            return 0;
        }

        return userRoleMapper.updateById( userRole );
    }

    @Override
    public UserRole selectOne(UserRole userRole) {
        if( null == userRole ) {
            log.warn("select userRole one, but userRole is null ...");
            return null;
        }
        userRole = userRoleMapper.selectOne( userRole );
        return userRole;
    }


    @Override
    public PagingData<UserRole> selectPage(UserRole userRole) {
        PagingData<UserRole> PagingData = new PagingData<>();

        if( null == userRole ) {
            log.warn("select userRole page, but userRole is null...");
            return PagingData;
        }

        Integer queryCount = userRoleMapper.selectByIndexCount( userRole );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select userRole page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<UserRole> userRoles =  selectByIndex( userRole );
        PagingData.setData( userRoles );
        return PagingData;
    }

    @Override
    public List<UserRole> selectByIndex(UserRole userRole) {
        List<UserRole> userRoles = new ArrayList<>();
        if( null == userRole ) {
            log.warn("select userRole by index, but userRole is null ...");
            return userRoles;
        }

        userRoles = userRoleMapper.selectByIndex( userRole );

        return userRoles;
    }
}
