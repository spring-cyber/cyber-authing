package com.cyber.authing.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import com.cyber.infrastructure.repository.BaseMapper;
import com.cyber.authing.domain.entity.Account;

import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    Integer deleteByUserId(long userId);

    List<Account> selectUserSystemAccount(List<String> userIds);
}
