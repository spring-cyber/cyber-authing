package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Permission;
import com.cyber.authing.mapper.PermissionMapper;
import com.cyber.authing.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {

	private static final Logger LOGGER = LoggerFactory.getLogger( PermissionServiceImpl.class );

    @Autowired
    PermissionMapper permissionMapper;
    
    
    @Override
    @Transactional(readOnly = false)
    public Integer save(Permission permission) {

        if( null == permission) {
            LOGGER.warn("save permission, but permission is null...");
            return 0;
        }

        return permissionMapper.insert(permission);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(Permission permission) {

        if( null == permission) {
            LOGGER.warn("delete permission, but permission is null  or permission id is null...");
            return 0;
        }

        return permissionMapper.deleteById(permission);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(Permission permission) {

        if( null == permission) {
            LOGGER.warn("update permission, but permission is null  or permission id is null...");
            return 0;
        }

        return permissionMapper.updateById(permission);
    }

    @Override
    public Permission selectOne(Permission permission) {
        if( permission == null) {
            LOGGER.warn("select permission one, but permission is null ...");
            return null;
        }
        permission = permissionMapper.selectOne( permission );
        return permission;
    }
//
//
//    @Override
//    public PagingData<Permission> selectPage(Permission permission) {
//        PagingData<Permission> pagingData = new PagingData<Permission>();
//
//        if( null == permission ) {
//            LOGGER.warn("select permission page, but permission is null...");
//            return pagingData;
//        }
//
//        Integer queryCount = permissionMapper.selectByIndexCount( permission );
//        pagingData.setTotal( queryCount );
//
//        if( null != queryCount && queryCount <= 0 ) {
//            LOGGER.info("select permission page , but count {} == 0 ...",queryCount);
//            return pagingData;
//        }
//
//        List<Permission> permissions =  selectByIndex( permission );
//        pagingData.setRows(permissions);
//        return pagingData;
//    }
//
//    @Override
//    public List<Permission> selectByIndex(Permission permission) {
//        List<Permission> permissions = new ArrayList<Permission>();
//        if( permission == null) {
//            LOGGER.warn("select permission by index, but permission is null ...");
//            return permissions;
//        }
//
//        permissions = permissionMapper.selectByIndex( permission );
//
//        return permissions;
//    }
}
