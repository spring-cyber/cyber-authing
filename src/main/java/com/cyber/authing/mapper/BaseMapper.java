package com.cyber.authing.mapper;

import java.util.List;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/9
 */
public interface BaseMapper<T> {

    Integer save(T entity);

    Integer deleteById(T entity);

    Integer updateById(T entity);

    T selectOne(T entity);

    Integer selectByIndexCount(T entity);

    List<T> selectByIndex(T entity);
}
