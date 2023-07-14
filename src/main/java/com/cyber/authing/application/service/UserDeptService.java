package com.cyber.authing.application.service;

import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.UserDept;

import java.util.List;

public interface UserDeptService extends BaseService<UserDept> {


    List<UserDept> selectUserDept(List<String> userIds);

    Integer saveBatch(List<UserDept> userDepts);

    List<UserDept> selectList(UserDept userDept);

    Integer deleteByUserId(long userId);
}
