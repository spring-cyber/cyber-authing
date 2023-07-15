package com.cyber.authing.domain.repository;

import com.cyber.authing.domain.entity.Role;
import com.cyber.infrastructure.repository.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectList(Role role);
}
