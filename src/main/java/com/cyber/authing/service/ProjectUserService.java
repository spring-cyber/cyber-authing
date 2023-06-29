package com.cyber.authing.service;

import com.cyber.authing.entity.domain.ProjectUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectUserService extends BaseService<ProjectUser> {
    @Transactional(readOnly = false)
    Integer batchSaveUpdate(List<ProjectUser> projectUserList);

    @Transactional(readOnly = false)
    Integer deleteByIds(List<Long> ids);
}
