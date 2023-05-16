package com.cyber.authing.mapper;

import com.cyber.mybatiesplus.core.mapper.BaseMapperX;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/9
 */
public interface BaseMapperPlus<T> extends BaseMapperX<T> {
    T selectOne(T t);
}
