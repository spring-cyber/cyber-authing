package com.cyber.authing.application.service;

import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.Position;
import com.cyber.authing.domain.response.CountStatus;

import java.util.List;

public interface PositionService extends BaseService<Position> {


    List<Position> selectList(Position position);

    List<CountStatus> countStatus();

}
