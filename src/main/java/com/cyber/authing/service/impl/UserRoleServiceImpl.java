package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.UserRole;
import com.cyber.authing.mapper.UserRoleMapper;
import com.cyber.authing.service.UserRoleService;
import com.cyber.domain.entity.PagingResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    UserRoleMapper userRoleMapper;


    @Override
    @Transactional(readOnly = false)
    public Integer save(UserRole userRole) {

        if (null == userRole) {
            LOGGER.warn("save userRole, but userRole is null...");
            return 0;
        }

        return userRoleMapper.save(userRole);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer batchSave(List<UserRole> userRoleList) {
        if (CollectionUtils.isEmpty(userRoleList)) {
            LOGGER.warn("batch save UserRole, but UserRole list is empty...");
            return 0;
        }
        return userRoleMapper.batchSave(userRoleList);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(UserRole userRole) {

        if (null == userRole) {
            LOGGER.warn("delete userRole, but userRole is null  or userRole id is null...");
            return 0;
        }

        return userRoleMapper.deleteById(userRole);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            LOGGER.warn("delete userRole, but userRole list is null  or userRole list id is empty...");
            return 0;
        }
        return userRoleMapper.deleteByIds(ids);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(UserRole userRole) {

        if (null == userRole) {
            LOGGER.warn("update userRole, but userRole is null  or userRole id is null...");
            return 0;
        }

        return userRoleMapper.updateById(userRole);
    }

    @Override
    public UserRole selectOne(UserRole userRole) {
        if (userRole == null) {
            LOGGER.warn("select userRole one, but userRole is null ...");
            return null;
        }
        userRole = userRoleMapper.selectOne(userRole);
        return userRole;
    }

    @Override
    public List<String> selectPermsByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            LOGGER.warn("select user perms return null, userId is null ...");
            return null;
        }
        return userRoleMapper.selectPermsByUserId(userId);
    }


    @Override
    public PagingResponse<UserRole> selectPage(UserRole userRole) {
        PagingResponse<UserRole> pagingData = new PagingResponse<UserRole>();

        if (null == userRole) {
            LOGGER.warn("select userRole page, but userRole is null...");
            return pagingData;
        }

        Integer queryCount = userRoleMapper.selectByIndexCount(userRole);
        pagingData.setRow(queryCount);

        if (queryCount <= 0) {
            LOGGER.info("select userRole page , but count {} == 0 ...", queryCount);
            return pagingData;
        }
        pagingData.setData(selectByIndex(userRole));
        return pagingData;
    }

    @Override
    public List<UserRole> selectByIndex(UserRole userRole) {
        List<UserRole> userRoles = new ArrayList<UserRole>();
        if (userRole == null) {
            LOGGER.warn("select userRole by index, but userRole is null ...");
            return userRoles;
        }

        userRoles = userRoleMapper.selectByIndex(userRole);
        return userRoles;
    }
}
