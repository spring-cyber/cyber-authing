package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.UserPosition;

import java.util.List;

@Mapper
public interface UserPositionMapper extends BaseMapper<UserPosition> {


    Integer saveBatch(List<UserPosition> list);

    Integer deleteByUserId(Long userId);

    List<UserPosition> selectUserPosition(List<String> userIds);
}
