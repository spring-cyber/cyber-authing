package com.cyber.authing.application.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cyber.authing.application.service.AccountService;
import com.cyber.authing.domain.entity.Account;
import com.cyber.authing.domain.repository.AccountMapper;
import com.cyber.domain.entity.PagingData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public Integer save(Account account) {

        if( null == account ) {
            log.warn("save account, but account is null...");
            return 0;
        }

        account.setId(IdUtil.simpleUUID());
        return accountMapper.save( account );
    }

    @Override
    @Transactional
    public Integer deleteById(Account account) {

        if( null == account ) {
            log.warn("delete account, but account is null  or account id is null...");
            return 0;
        }

        return accountMapper.deleteById( account );
    }

    @Override
    @Transactional
    public Integer updateById(Account account) {

        if( null == account ) {
            log.warn("update account, but account is null  or account id is null...");
            return 0;
        }

        return accountMapper.updateById( account );
    }

    @Override
    public Account selectOne(Account account) {
        if( null == account ) {
            log.warn("select account one, but account is null ...");
            return null;
        }
        account = accountMapper.selectOne( account );
        return account;
    }


    @Override
    public PagingData<Account> selectPage(Account account) {
        PagingData<Account> PagingData = new PagingData<>();

        if( null == account ) {
            log.warn("select account page, but account is null...");
            return PagingData;
        }

        Integer queryCount = accountMapper.selectByIndexCount( account );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select account page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<Account> accounts =  selectByIndex( account );
        PagingData.setData( accounts );
        return PagingData;
    }

    @Override
    public List<Account> selectByIndex(Account account) {
        List<Account> accounts = new ArrayList<>();
        if( null == account ) {
            log.warn("select account by index, but account is null ...");
            return accounts;
        }

        accounts = accountMapper.selectByIndex( account );

        return accounts;
    }

    @Override
    public Integer deleteByUserId(long userId) {
        return accountMapper.deleteByUserId(userId);
    }

    @Override
    public List<Account> selectUserSystemAccount(List<String> userIds) {
        return accountMapper.selectUserSystemAccount(userIds);
    }
}
