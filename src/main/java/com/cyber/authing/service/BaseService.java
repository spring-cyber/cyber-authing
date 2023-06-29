package com.cyber.authing.service;

import com.cyber.domain.entity.PagingResponse;

import java.util.List;

public interface BaseService<T> {
    Integer save(T t);

    Integer deleteById(T t);

    Integer updateById(T t);

    T selectOne(T t);

    PagingResponse<T> selectPage(T t);

    List<T> selectByIndex(T t);
}
