package com.cyber.authing.domain.repository;

import com.cyber.authing.domain.response.CountStatus;
import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.Enterprise;

import java.util.List;

@Mapper
public interface EnterpriseMapper extends BaseMapper<Enterprise> {

    List<Enterprise> selectList(Enterprise enterprise);

    List<CountStatus> countStatus();

}
