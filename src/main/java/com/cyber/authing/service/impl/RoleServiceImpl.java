package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Role;
import com.cyber.authing.mapper.RoleMapper;
import com.cyber.authing.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger( RoleServiceImpl.class );

    @Autowired
    RoleMapper roleMapper;
    
    
    @Override
    @Transactional(readOnly = false)
    public Integer save(Role role) {

        if( null == role) {
            LOGGER.warn("save role, but role is null...");
            return 0;
        }

        return roleMapper.insert(role);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(Role role) {

        if( null == role) {
            LOGGER.warn("delete role, but role is null  or role id is null...");
            return 0;
        }

        return roleMapper.deleteById(role);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(Role role) {

        if( null == role) {
            LOGGER.warn("update role, but role is null  or role id is null...");
            return 0;
        }

        return roleMapper.updateById(role);
    }

    @Override
    public Role selectOne(Role role) {
        if( role == null) {
            LOGGER.warn("select role one, but role is null ...");
            return null;
        }
        role = roleMapper.selectOne( role );
        return role;
    }


//    @Override
//    public PagingData<Role> selectPage(Role role) {
//        PagingData<Role> pagingData = new PagingData<Role>();
//
//        if( null == role ) {
//            LOGGER.warn("select role page, but role is null...");
//            return pagingData;
//        }
//
//        Integer queryCount = roleMapper.selectByIndexCount( role );
//        pagingData.setTotal( queryCount );
//
//        if( null != queryCount && queryCount <= 0 ) {
//            LOGGER.info("select role page , but count {} == 0 ...",queryCount);
//            return pagingData;
//        }
//
//        List<Role> roles =  selectByIndex( role );
//        pagingData.setRows(roles);
//        return pagingData;
//    }
//
//    @Override
//    public List<Role> selectByIndex(Role role) {
//        List<Role> roles = new ArrayList<Role>();
//        if( role == null) {
//            LOGGER.warn("select role by index, but role is null ...");
//            return roles;
//        }
//
//        roles = roleMapper.selectByIndex( role );
//
//        return roles;
//    }
}
