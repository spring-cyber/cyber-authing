package com.cyber.authing.application.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.IdUtil;
import com.cyber.authing.application.service.AccountService;
import com.cyber.authing.application.service.UserDeptService;
import com.cyber.authing.application.service.UserPositionService;
import com.cyber.authing.domain.entity.Account;
import com.cyber.authing.domain.entity.UserDept;
import com.cyber.authing.domain.entity.UserPosition;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.UserMapper;
import com.cyber.authing.domain.entity.User;
import com.cyber.authing.application.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserPositionService userPositionService;

    private final UserDeptService userDeptService;

    private final AccountService accountService;

    @Override
    @Transactional
    public Integer save(User user) {

        if (null == user) {
            log.warn("save user, but user is null...");
            return 0;
        }

        user.setId(String.valueOf(IdUtil.getSnowflakeNextId()));

        saveDeptPositions(user);
        Account account = new Account();
        account.setAccount(user.getAccount());
        account.setPassword(user.getPassword());
        account.setUserId(Long.valueOf(user.getId()));
        account.setType(0);
        accountService.save(account);
        return userMapper.save(user);
    }

    /**
     * 保存用户部门 岗位 关系表
     *
     * @param user
     */
    private void saveDeptPositions(User user) {
        List<UserDept> userDepts = new ArrayList<>();
        List<UserPosition> userPositions = new ArrayList<>();
        if (null!= user.getDeptIds()) {
            //构建用户部门关系
            userDepts = user.getDeptIds().stream().map(deptId -> {
                UserDept userDept = new UserDept();
                userDept.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
                userDept.setUserId(Long.valueOf(user.getId()));
                userDept.setDeptId(deptId);
                return userDept;
            }).collect(Collectors.toList());
        }


        if (null!= user.getPositionIds()) {
            //构建用户岗位关系
            userPositions = user.getPositionIds().stream().map(positionId -> {
                UserPosition userDept = new UserPosition();
                userDept.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
                userDept.setUserId(Long.valueOf(user.getId()));
                userDept.setPositionId(positionId);
                return userDept;
            }).collect(Collectors.toList());
        }


        if (!userDepts.isEmpty()) {
            userDeptService.deleteByUserId(Long.parseLong(user.getId()));
            userDeptService.saveBatch(userDepts);
        }
        if (!userPositions.isEmpty()) {
            userPositionService.deleteByUserId(Long.parseLong(user.getId()));
            userPositionService.saveBatch(userPositions);
        }

    }


    @Override
    @Transactional
    public Integer deleteById(User user) {

        if (null == user) {
            log.warn("delete user, but user is null  or user id is null...");
            return 0;
        }
        //清除关系
        userPositionService.deleteByUserId(Long.parseLong(user.getId()));
        userDeptService.deleteByUserId(Long.parseLong(user.getId()));
        accountService.deleteByUserId(Long.parseLong(user.getId()));

        return userMapper.deleteById(user);
    }

    @Override
    @Transactional
    public Integer updateById(User user) {

        if (null == user) {
            log.warn("update user, but user is null  or user id is null...");
            return 0;
        }
        //保存关系
        saveDeptPositions(user);
        return userMapper.updateById(user);
    }

    @Override
    public User selectOne(User user) {
        if (null == user) {
            log.warn("select user one, but user is null ...");
            return null;
        }
        user = userMapper.selectOne(user);
        Account account = new Account();
        account.setUserId(Long.valueOf(user.getId()));
        account.setType(0);
        account = accountService.selectOne(account);

        user.setAccount(account.getAccount());
        user.setPassword(account.getPassword());

        List<UserPosition> positionInfo = userPositionService.selectUserPosition(Collections.singletonList(user.getId()));
        List<UserDept> userDeptInfo = userDeptService.selectUserDept(Collections.singletonList(user.getId()));

        user.setDeptIds(userDeptInfo!=null?userDeptInfo.stream().map(UserDept::getDeptId).collect(Collectors.toList()) : null);
        user.setPositionIds(positionInfo!=null?positionInfo.stream().map(UserPosition::getPositionId).collect(Collectors.toList()) : null);
        return user;
    }


    @Override
    public PagingData<User> selectPage(User user) {
        PagingData<User> PagingData = new PagingData<>();

        if (null == user) {
            log.warn("select user page, but user is null...");
            return PagingData;
        }

        Integer queryCount = userMapper.selectByIndexCount(user);
        PagingData.setRow(queryCount);

        if (queryCount <= 0) {
            log.info("select user page , but count {} == 0 ...", queryCount);
            return PagingData;
        }

        List<User> users = selectByIndex(user);

        PagingData.setData(users);

        //查询岗位和部门信息
        List<String> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        if (userIds.isEmpty()) {
            log.info("select userPosition and userDept, but userIds isEmpty ...");
            return PagingData;
        }
        //岗位信息
        List<UserPosition> positionInfo = userPositionService.selectUserPosition(userIds);
        Map<String, List<UserPosition>> userPositionCollect = positionInfo.stream().collect(Collectors.groupingBy(userPosition -> String.valueOf(userPosition.getUserId())));

        //部门信息
        List<UserDept> userDeptInfo = userDeptService.selectUserDept(userIds);
        Map<String, List<UserDept>> userDeptCollect = userDeptInfo.stream().collect(Collectors.groupingBy(userDept -> String.valueOf(userDept.getUserId())));

        //账号信息
        List<Account> userSystemAccount = accountService.selectUserSystemAccount(userIds);
        Map<String, Account> accountMap = userSystemAccount.stream().collect(Collectors.toMap(account -> String.valueOf(account.getUserId()),account -> account));

        users.forEach(userItem -> {
            userItem.setPositionInfo(userPositionCollect.getOrDefault(userItem.getId(), null));
            userItem.setPositionIds(userItem.getPositionInfo()!=null?userItem.getPositionInfo().stream().map(UserPosition::getPositionId).collect(Collectors.toList()) : null);

            userItem.setUserDeptInfo(userDeptCollect.getOrDefault(userItem.getId(), null));
            userItem.setDeptIds(userItem.getUserDeptInfo()!=null?userItem.getUserDeptInfo().stream().map(UserDept::getDeptId).collect(Collectors.toList()) : null);

            if (accountMap.containsKey(userItem.getId())) {
                Account account = accountMap.get(userItem.getId());
                userItem.setAccount(account.getAccount());
                userItem.setPassword(account.getPassword());
            }

        });

        return PagingData;
    }

    @Override
    public List<User> selectByIndex(User user) {
        List<User> users = new ArrayList<>();
        if (null == user) {
            log.warn("select user by index, but user is null ...");
            return users;
        }

        users = userMapper.selectByIndex(user);

        return users;
    }

    @Override
    public List<User> selectList(User user) {
        List<User> users = new ArrayList<>();
        if (null == user) {
            log.warn("select user by index, but user is null ...");
            return users;
        }
        users = userMapper.selectList(user);
        return users;
    }

    @Override
    public List<CountStatus> countStatus() {
        return userMapper.countStatus();
    }
}
