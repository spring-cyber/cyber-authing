package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Tenant;
import com.cyber.authing.mapper.TenantMapper;
import com.cyber.authing.service.TenantService;
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
public class TenantServiceImpl implements TenantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantServiceImpl.class);

    @Autowired
    TenantMapper tenantMapper;


    @Override
    @Transactional(readOnly = false)
    public Integer save(Tenant tenant) {

        if (null == tenant) {
            LOGGER.warn("save tenant, but tenant is null...");
            return 0;
        }

        return tenantMapper.save(tenant);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(Tenant tenant) {

        if (null == tenant) {
            LOGGER.warn("delete tenant, but tenant is null  or tenant id is null...");
            return 0;
        }

        return tenantMapper.deleteById(tenant);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(Tenant tenant) {

        if (null == tenant) {
            LOGGER.warn("update tenant, but tenant is null  or tenant id is null...");
            return 0;
        }

        return tenantMapper.updateById(tenant);
    }

    @Override
    public Tenant selectOne(Tenant tenant) {
        if (tenant == null) {
            LOGGER.warn("select tenant one, but tenant is null ...");
            return null;
        }
        tenant = tenantMapper.selectOne(tenant);
        return tenant;
    }


    @Override
    public PagingResponse<Tenant> selectPage(Tenant tenant) {
        PagingResponse<Tenant> pagingData = new PagingResponse<Tenant>();

        if (null == tenant) {
            LOGGER.warn("select tenant page, but tenant is null...");
            return pagingData;
        }

        Integer queryCount = tenantMapper.selectByIndexCount(tenant);
        pagingData.setRow(queryCount);

        if (queryCount <= 0) {
            LOGGER.info("select tenant page , but count {} == 0 ...", queryCount);
            return pagingData;
        }
        pagingData.setData(selectByIndex(tenant));
        return pagingData;
    }

    @Override
    public List<Tenant> selectByIndex(Tenant tenant) {
        List<Tenant> tenants = new ArrayList<Tenant>();
        if (tenant == null) {
            LOGGER.warn("select tenant by index, but tenant is null ...");
            return tenants;
        }

        tenants = tenantMapper.selectByIndex(tenant);
        return tenants;
    }
}
