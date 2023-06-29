package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Role;
import com.cyber.authing.entity.domain.RolePermission;
import com.cyber.authing.mapper.RoleMapper;
import com.cyber.authing.mapper.RolePermissionMapper;
import com.cyber.authing.service.RoleService;
import com.cyber.domain.entity.PagingResponse;
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
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;


    @Override
    @Transactional(readOnly = false)
    public Integer save(Role role) {

        if (null == role) {
            LOGGER.warn("save role, but role is null...");
            return 0;
        }
        int result = roleMapper.save(role);
        if (result > 0 && !CollectionUtils.isEmpty(role.getPermissionList())) {
            List<RolePermission> rolePermissionList = new ArrayList<>();
            role.getPermissionList().forEach(permission -> {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(Long.valueOf(role.getId()));
                        rolePermission.setPermissionId(Long.valueOf(permission.getId()));
                        rolePermission.setCreator(role.getCreator());
                        rolePermission.setUpdator(role.getCreator());
                        rolePermission.setCreateTime(role.getCreateTime());
                        rolePermission.setUpdateTime(role.getCreateTime());
                        rolePermissionList.add(rolePermission);
                    }
            );
            result = rolePermissionMapper.batchSave(rolePermissionList);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(Role role) {

        if (null == role) {
            LOGGER.warn("delete role, but role is null  or role id is null...");
            return 0;
        }

        return roleMapper.deleteById(role);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(Role role) {

        if (null == role) {
            LOGGER.warn("update role, but role is null  or role id is null...");
            return 0;
        }

        return roleMapper.updateById(role);
    }

    @Override
    public Role selectOne(Role role) {
        if (role == null) {
            LOGGER.warn("select role one, but role is null ...");
            return null;
        }
        role = roleMapper.selectOne(role);
        return role;
    }


    @Override
    public PagingResponse<Role> selectPage(Role role) {
        PagingResponse<Role> pagingData = new PagingResponse<Role>();

        if (null == role) {
            LOGGER.warn("select role page, but role is null...");
            return pagingData;
        }

        Integer queryCount = roleMapper.selectByIndexCount(role);
        pagingData.setRow(queryCount);

        if (queryCount <= 0) {
            LOGGER.info("select role page , but count {} == 0 ...", queryCount);
            return pagingData;
        }
        pagingData.setData(selectByIndex(role));
        return pagingData;
    }

    @Override
    public List<Role> selectByIndex(Role role) {
        List<Role> roles = new ArrayList<Role>();
        if (role == null) {
            LOGGER.warn("select role by index, but role is null ...");
            return roles;
        }
        roles = roleMapper.selectByIndex(role);
        return roles;
    }
}
