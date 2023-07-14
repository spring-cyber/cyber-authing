package com.cyber.authing.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.SysConfigMapper;
import com.cyber.authing.domain.entity.SysConfig;
import com.cyber.authing.application.service.SysConfigService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    @Override
    @Transactional
    public Integer save(SysConfig sysConfig) {

        if( null == sysConfig ) {
            log.warn("save sysConfig, but sysConfig is null...");
            return 0;
        }
        sysConfig.setId(String.valueOf(IdUtil.getSnowflakeNextId()));

        return sysConfigMapper.save( sysConfig );
    }

    @Override
    @Transactional
    public Integer deleteById(SysConfig sysConfig) {

        if( null == sysConfig ) {
            log.warn("delete sysConfig, but sysConfig is null  or sysConfig id is null...");
            return 0;
        }

        return sysConfigMapper.deleteById( sysConfig );
    }

    @Override
    @Transactional
    public Integer updateById(SysConfig sysConfig) {

        if( null == sysConfig ) {
            log.warn("update sysConfig, but sysConfig is null  or sysConfig id is null...");
            return 0;
        }

        return sysConfigMapper.updateById( sysConfig );
    }

    @Override
    public SysConfig selectOne(SysConfig sysConfig) {
        if( null == sysConfig ) {
            log.warn("select sysConfig one, but sysConfig is null ...");
            return null;
        }
        sysConfig = sysConfigMapper.selectOne( sysConfig );
        return sysConfig;
    }


    @Override
    public PagingData<SysConfig> selectPage(SysConfig sysConfig) {
        PagingData<SysConfig> PagingData = new PagingData<>();

        if( null == sysConfig ) {
            log.warn("select sysConfig page, but sysConfig is null...");
            return PagingData;
        }

        Integer queryCount = sysConfigMapper.selectByIndexCount( sysConfig );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select sysConfig page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<SysConfig> sysConfigs =  selectByIndex( sysConfig );
        PagingData.setData( sysConfigs );
        return PagingData;
    }

    @Override
    public List<SysConfig> selectByIndex(SysConfig sysConfig) {
        List<SysConfig> sysConfigs = new ArrayList<>();
        if( null == sysConfig ) {
            log.warn("select sysConfig by index, but sysConfig is null ...");
            return sysConfigs;
        }

        sysConfigs = sysConfigMapper.selectByIndex( sysConfig );

        return sysConfigs;
    }
}
