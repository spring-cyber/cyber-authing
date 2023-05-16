package com.cyber.authing.service;

public interface BaseService<T> {
    Integer save(T t);

    Integer deleteById(T t);

    Integer updateById(T t);

    T selectOne(T t);
}
