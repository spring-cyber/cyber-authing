package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.UserExtension;
import com.cyber.authing.mapper.UserExtensionMapper;
import com.cyber.authing.service.UserExtensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class UserExtensionServiceImpl implements UserExtensionService {

	private static final Logger LOGGER = LoggerFactory.getLogger( UserExtensionServiceImpl.class );

    @Autowired
    UserExtensionMapper userExtensionMapper;
    
    
    @Override
    @Transactional(readOnly = false)
    public Integer save(UserExtension userExtension) {

        if( null == userExtension) {
            LOGGER.warn("save userExtension, but userExtension is null...");
            return 0;
        }

        return userExtensionMapper.insert(userExtension);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(UserExtension userExtension) {

        if( null == userExtension) {
            LOGGER.warn("delete userExtension, but userExtension is null  or userExtension id is null...");
            return 0;
        }

        return userExtensionMapper.deleteById(userExtension);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(UserExtension userExtension) {

        if( null == userExtension) {
            LOGGER.warn("update userExtension, but userExtension is null  or userExtension id is null...");
            return 0;
        }

        return userExtensionMapper.updateById(userExtension);
    }

    @Override
    public UserExtension selectOne(UserExtension userExtension) {
        if( userExtension == null) {
            LOGGER.warn("select userExtension one, but userExtension is null ...");
            return null;
        }
        userExtension = userExtensionMapper.selectOne( userExtension );
        return userExtension;
    }


//    @Override
//    public PagingData<UserExtension> selectPage(UserExtension userExtension) {
//        PagingData<UserExtension> pagingData = new PagingData<UserExtension>();
//
//        if( null == userExtension ) {
//            LOGGER.warn("select userExtension page, but userExtension is null...");
//            return pagingData;
//        }
//
//        Integer queryCount = userExtensionMapper.selectByIndexCount( userExtension );
//        pagingData.setTotal( queryCount );
//
//        if( null != queryCount && queryCount <= 0 ) {
//            LOGGER.info("select userExtension page , but count {} == 0 ...",queryCount);
//            return pagingData;
//        }
//
//        List<UserExtension> userExtensions =  selectByIndex( userExtension );
//        pagingData.setRows(userExtensions);
//        return pagingData;
//    }
//
//    @Override
//    public List<UserExtension> selectByIndex(UserExtension userExtension) {
//        List<UserExtension> userExtensions = new ArrayList<UserExtension>();
//        if( userExtension == null) {
//            LOGGER.warn("select userExtension by index, but userExtension is null ...");
//            return userExtensions;
//        }
//
//        userExtensions = userExtensionMapper.selectByIndex( userExtension );
//
//        return userExtensions;
//    }
}
