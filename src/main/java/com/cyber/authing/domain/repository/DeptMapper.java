package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.Dept;

import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    List<Dept> selectList(Dept dept);
}
