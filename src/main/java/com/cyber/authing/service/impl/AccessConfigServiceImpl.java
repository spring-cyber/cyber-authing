package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.AccessConfig;
import com.cyber.authing.mapper.AccessConfigMapper;
import com.cyber.authing.service.AccessConfigService;
import com.cyber.domain.entity.PagingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class AccessConfigServiceImpl implements AccessConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessConfigServiceImpl.class);

    @Autowired
    AccessConfigMapper accessConfigMapper;


    @Override
    @Transactional(readOnly = false)
    public Integer save(AccessConfig accessConfig) {

        if (null == accessConfig) {
            LOGGER.warn("save accessConfig, but accessConfig is null...");
            return 0;
        }

        return accessConfigMapper.save(accessConfig);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(AccessConfig accessConfig) {

        if (null == accessConfig) {
            LOGGER.warn("delete accessConfig, but accessConfig is null  or accessConfig id is null...");
            return 0;
        }

        return accessConfigMapper.deleteById(accessConfig);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(AccessConfig accessConfig) {

        if (null == accessConfig) {
            LOGGER.warn("update accessConfig, but accessConfig is null  or accessConfig id is null...");
            return 0;
        }

        return accessConfigMapper.updateById(accessConfig);
    }

    @Override
    public AccessConfig selectOne(AccessConfig accessConfig) {
        if (accessConfig == null) {
            LOGGER.warn("select accessConfig one, but accessConfig is null ...");
            return null;
        }
        accessConfig = accessConfigMapper.selectOne(accessConfig);
        return accessConfig;
    }


    @Override
    public PagingResponse<AccessConfig> selectPage(AccessConfig accessConfig) {
        PagingResponse<AccessConfig> pagingData = new PagingResponse<AccessConfig>();

        if (null == accessConfig) {
            LOGGER.warn("select accessConfig page, but accessConfig is null...");
            return pagingData;
        }

        Integer queryCount = accessConfigMapper.selectByIndexCount(accessConfig);
        pagingData.setRow(queryCount);

        if (null != queryCount && queryCount <= 0) {
            LOGGER.info("select accessConfig page , but count {} == 0 ...", queryCount);
            return pagingData;
        }
        pagingData.setData(selectByIndex(accessConfig));
        return pagingData;
    }

    @Override
    public List<AccessConfig> selectByIndex(AccessConfig accessConfig) {
        List<AccessConfig> accessConfigs = new ArrayList<AccessConfig>();
        if (accessConfig == null) {
            LOGGER.warn("select accessConfig by index, but accessConfig is null ...");
            return accessConfigs;
        }

        accessConfigs = accessConfigMapper.selectByIndex(accessConfig);
        return accessConfigs;
    }
}
