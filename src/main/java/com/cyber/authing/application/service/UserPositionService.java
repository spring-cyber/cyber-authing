package com.cyber.authing.application.service;

import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.UserPosition;

import java.util.List;

public interface UserPositionService extends BaseService<UserPosition> {


    Integer saveBatch(List<UserPosition> list);

    List<UserPosition> selectUserPosition(List<String> userIds);

    Integer deleteByUserId(long userId);
}
