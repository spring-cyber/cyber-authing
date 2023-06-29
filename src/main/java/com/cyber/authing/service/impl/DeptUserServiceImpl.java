package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.DeptUser;
import com.cyber.authing.mapper.DeptUserMapper;
import com.cyber.authing.service.DeptUserService;
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
public class DeptUserServiceImpl implements DeptUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeptUserServiceImpl.class);

    @Autowired
    DeptUserMapper deptUserMapper;


    @Override
    @Transactional(readOnly = false)
    public Integer save(DeptUser deptUser) {
        if (null == deptUser) {
            LOGGER.warn("save deptUser, but deptUser is null...");
            return 0;
        }
        return deptUserMapper.save(deptUser);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(DeptUser deptUser) {
        if (null == deptUser) {
            LOGGER.warn("delete deptUser, but deptUser is null  or deptUser id is null...");
            return 0;
        }
        return deptUserMapper.deleteById(deptUser);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            LOGGER.warn("delete deptUser, but deptUser list is null  or deptUser list id is empty...");
            return 0;
        }
        return deptUserMapper.deleteByIds(ids);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(DeptUser deptUser) {
        if (null == deptUser) {
            LOGGER.warn("update deptUser, but deptUser is null  or deptUser id is null...");
            return 0;
        }
        return deptUserMapper.updateById(deptUser);
    }

    @Override
    public DeptUser selectOne(DeptUser deptUser) {
        if (deptUser == null) {
            LOGGER.warn("select deptUser one, but deptUser is null ...");
            return null;
        }
        deptUser = deptUserMapper.selectOne(deptUser);
        return deptUser;
    }


    @Override
    public PagingResponse<DeptUser> selectPage(DeptUser deptUser) {
        PagingResponse<DeptUser> pagingData = new PagingResponse<DeptUser>();

        if (null == deptUser) {
            LOGGER.warn("select deptUser page, but deptUser is null...");
            return pagingData;
        }
        Integer queryCount = deptUserMapper.selectByIndexCount(deptUser);
        pagingData.setRow(queryCount);
        if (queryCount <= 0) {
            LOGGER.info("select deptUser page , but count {} == 0 ...", queryCount);
            return pagingData;
        }
        pagingData.setData(selectByIndex(deptUser));
        return pagingData;
    }

    @Override
    public List<DeptUser> selectByIndex(DeptUser deptUser) {
        List<DeptUser> deptUsers = new ArrayList<DeptUser>();
        if (deptUser == null) {
            LOGGER.warn("select deptUser by index, but deptUser is null ...");
            return deptUsers;
        }
        deptUsers = deptUserMapper.selectByIndex(deptUser);
        return deptUsers;
    }
}
