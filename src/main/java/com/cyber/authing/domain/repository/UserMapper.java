package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    Integer saveBatch(List<User> list);

}
