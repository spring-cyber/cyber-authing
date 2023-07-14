package com.cyber.authing.application;

import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.User;
import com.cyber.authing.domain.response.CountStatus;

import java.util.List;

public interface UserService extends BaseService<User> {


    List<User> selectList(User user);

    List<CountStatus> countStatus();

}
