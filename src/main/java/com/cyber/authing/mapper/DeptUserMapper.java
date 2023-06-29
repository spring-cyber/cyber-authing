package com.cyber.authing.mapper;

import com.cyber.authing.entity.domain.DeptUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptUserMapper extends BaseMapper<DeptUser> {
    Integer deleteByIds(List<Long> ids);
}