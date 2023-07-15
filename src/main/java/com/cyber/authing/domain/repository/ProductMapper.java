package com.cyber.authing.domain.repository;

import com.cyber.authing.domain.entity.Product;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.infrastructure.repository.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    List<CountStatus> countStatus();

    List<Product> selectList(Product product);
}
