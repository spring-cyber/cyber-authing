package com.cyber.authing.application.service;

import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.Account;

import java.util.List;

public interface AccountService extends BaseService<Account> {


    Integer deleteByUserId(long userId);

    List<Account> selectUserSystemAccount(List<String> userIds);
}
