package com.cyber.authing.domain.repository;

import com.cyber.authing.domain.entity.Menu;
import com.cyber.infrastructure.repository.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectList(Menu menu);
}
