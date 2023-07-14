package com.cyber.authing.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.UserExtensionMapper;
import com.cyber.authing.domain.entity.UserExtension;
import com.cyber.authing.application.service.UserExtensionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserExtensionServiceImpl implements UserExtensionService {

    private final UserExtensionMapper userExtensionMapper;

    @Override
    @Transactional
    public Integer save(UserExtension userExtension) {

        if( null == userExtension ) {
            log.warn("save userExtension, but userExtension is null...");
            return 0;
        }

        userExtension.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        return userExtensionMapper.save( userExtension );
    }

    @Override
    @Transactional
    public Integer deleteById(UserExtension userExtension) {

        if( null == userExtension ) {
            log.warn("delete userExtension, but userExtension is null  or userExtension id is null...");
            return 0;
        }

        return userExtensionMapper.deleteById( userExtension );
    }

    @Override
    @Transactional
    public Integer updateById(UserExtension userExtension) {

        if( null == userExtension ) {
            log.warn("update userExtension, but userExtension is null  or userExtension id is null...");
            return 0;
        }

        return userExtensionMapper.updateById( userExtension );
    }

    @Override
    public UserExtension selectOne(UserExtension userExtension) {
        if( null == userExtension ) {
            log.warn("select userExtension one, but userExtension is null ...");
            return null;
        }
        userExtension = userExtensionMapper.selectOne( userExtension );
        return userExtension;
    }


    @Override
    public PagingData<UserExtension> selectPage(UserExtension userExtension) {
        PagingData<UserExtension> PagingData = new PagingData<>();

        if( null == userExtension ) {
            log.warn("select userExtension page, but userExtension is null...");
            return PagingData;
        }

        Integer queryCount = userExtensionMapper.selectByIndexCount( userExtension );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select userExtension page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<UserExtension> userExtensions =  selectByIndex( userExtension );
        PagingData.setData( userExtensions );
        return PagingData;
    }

    @Override
    public List<UserExtension> selectByIndex(UserExtension userExtension) {
        List<UserExtension> userExtensions = new ArrayList<>();
        if( null == userExtension ) {
            log.warn("select userExtension by index, but userExtension is null ...");
            return userExtensions;
        }

        userExtensions = userExtensionMapper.selectByIndex( userExtension );

        return userExtensions;
    }
}
