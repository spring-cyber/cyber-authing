package com.cyber.authing.domain.repository;

import com.cyber.authing.domain.response.CountStatus;
import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    List<CountStatus> countStatus();
}
