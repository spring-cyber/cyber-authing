package com.cyber.authing.service;

import com.cyber.authing.entity.domain.DeptUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeptUserService extends BaseService<DeptUser> {
    @Transactional(readOnly = false)
    Integer deleteByIds(List<Long> ids);
}
