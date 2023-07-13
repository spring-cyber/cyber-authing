package com.cyber.authing.application;

import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.Position;

import java.util.List;

public interface PositionService extends BaseService<Position> {


    List<Position> selectList(Position position);
}
