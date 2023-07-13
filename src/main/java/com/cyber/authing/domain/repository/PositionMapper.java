package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.Position;

import java.util.List;

@Mapper
public interface PositionMapper extends BaseMapper<Position> {

    List<Position> selectList(Position position);
}
