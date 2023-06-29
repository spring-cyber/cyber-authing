package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.ProjectUser;
import com.cyber.authing.mapper.ProjectUserMapper;
import com.cyber.authing.service.ProjectUserService;
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
public class ProjectUserServiceImpl implements ProjectUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectUserServiceImpl.class);

    @Autowired
    ProjectUserMapper projectUserMapper;


    @Override
    @Transactional(readOnly = false)
    public Integer save(ProjectUser projectUser) {

        if (null == projectUser) {
            LOGGER.warn("save projectUser, but projectUser is null...");
            return 0;
        }

        return projectUserMapper.save(projectUser);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer batchSaveUpdate(List<ProjectUser> projectUserList) {
        if (CollectionUtils.isEmpty(projectUserList)) {
            LOGGER.warn("batch save projectUser, but projectUser list is empty...");
            return 0;
        }
        return projectUserMapper.batchSaveAndUpdate(projectUserList);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(ProjectUser projectUser) {

        if (null == projectUser) {
            LOGGER.warn("delete projectUser, but projectUser is null  or projectUser id is null...");
            return 0;
        }

        return projectUserMapper.deleteById(projectUser);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            LOGGER.warn("delete projectUser, but projectUser list is null  or projectUser list id is empty...");
            return 0;
        }
        return projectUserMapper.deleteByIds(ids);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(ProjectUser projectUser) {

        if (null == projectUser) {
            LOGGER.warn("update projectUser, but projectUser is null  or projectUser id is null...");
            return 0;
        }

        return projectUserMapper.updateById(projectUser);
    }

    @Override
    public ProjectUser selectOne(ProjectUser projectUser) {
        if (projectUser == null) {
            LOGGER.warn("select projectUser one, but projectUser is null ...");
            return null;
        }
        projectUser = projectUserMapper.selectOne(projectUser);
        return projectUser;
    }


    @Override
    public PagingResponse<ProjectUser> selectPage(ProjectUser projectUser) {
        PagingResponse<ProjectUser> pagingData = new PagingResponse<ProjectUser>();

        if (null == projectUser) {
            LOGGER.warn("select projectUser page, but projectUser is null...");
            return pagingData;
        }
        Integer queryCount = projectUserMapper.selectByIndexCount(projectUser);
        pagingData.setRow(queryCount);

        if (queryCount <= 0) {
            LOGGER.info("select projectUser page , but count {} == 0 ...", queryCount);
            return pagingData;
        }
        pagingData.setData(selectByIndex(projectUser));
        return pagingData;
    }

    @Override
    public List<ProjectUser> selectByIndex(ProjectUser projectUser) {
        List<ProjectUser> projectUsers = new ArrayList<ProjectUser>();
        if (projectUser == null) {
            LOGGER.warn("select projectUser by index, but projectUser is null ...");
            return projectUsers;
        }

        projectUsers = projectUserMapper.selectByIndex(projectUser);

        return projectUsers;
    }
}
