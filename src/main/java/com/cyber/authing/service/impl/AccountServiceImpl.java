package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Account;
import com.cyber.authing.mapper.AccountMapper;
import com.cyber.authing.service.AccountService;
import com.cyber.domain.entity.PagingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    AccountMapper accountMapper;


    @Override
    @Transactional(readOnly = false)
    public Integer save(Account account) {

        if (null == account) {
            LOGGER.warn("save account, but account is null...");
            return 0;
        }

        return accountMapper.save(account);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(Account account) {

        if (null == account) {
            LOGGER.warn("delete account, but account is null  or account id is null...");
            return 0;
        }

        return accountMapper.deleteById(account);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(Account account) {

        if (null == account) {
            LOGGER.warn("update account, but account is null  or account id is null...");
            return 0;
        }

        return accountMapper.updateById(account);
    }

    @Override
    public Account selectOne(Account account) {
        if (account == null) {
            LOGGER.warn("select account one, but account is null ...");
            return null;
        }
        account = accountMapper.selectOne(account);
        return account;
    }


    @Override
    public PagingResponse<Account> selectPage(Account account) {
        PagingResponse<Account> pagingResponse = new PagingResponse<>();

        if (null == account) {
            LOGGER.warn("select account page, but account is null...");
            return pagingResponse;
        }

        Integer queryCount = accountMapper.selectByIndexCount(account);
        pagingResponse.setRow(queryCount);
        if (queryCount <= 0) {
            LOGGER.info("select account page , but count {} == 0 ...", queryCount);
            return pagingResponse;
        }
        pagingResponse.setData(selectByIndex(account));
        return pagingResponse;
    }

    @Override
    public List<Account> selectByIndex(Account account) {
        List<Account> accounts = new ArrayList<>();
        if (account == null) {
            LOGGER.warn("select account by index, but account is null ...");
            return accounts;
        }
        accounts = accountMapper.selectByIndex(account);
        return accounts;
    }
}
