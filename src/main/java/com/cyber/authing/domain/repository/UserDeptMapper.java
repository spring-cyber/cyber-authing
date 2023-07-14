package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.UserDept;

import java.util.List;

@Mapper
public interface UserDeptMapper extends BaseMapper<UserDept> {

    List<UserDept> selectUserDept(List<String> userIds);

    Integer saveBatch(List<UserDept> list);

    List<UserDept> selectList(UserDept userDept);

    Integer deleteByUserId(long userId);
}
