package com.cyber.authing.service;

import com.cyber.authing.entity.domain.UserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRoleService extends BaseService<UserRole> {

    @Transactional(readOnly = false)
    Integer batchSave(List<UserRole> userRoleList);

    @Transactional(readOnly = false)
    Integer deleteByIds(List<Long> ids);

    List<String> selectPermsByUserId(String userId);
}
