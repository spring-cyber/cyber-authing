package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Account;
import com.cyber.authing.entity.domain.User;
import com.cyber.authing.entity.dto.UserDTO;
import com.cyber.authing.mapper.AccountMapper;
import com.cyber.authing.mapper.UserMapper;
import com.cyber.authing.service.UserService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;
    @Autowired
    AccountMapper accountMapper;

    @Override
    @Transactional(readOnly = false)
    public Integer save(User user) {
        if (null == user) {
            LOGGER.warn("save user, but user is null...");
            return 0;
        }
        if (!(user instanceof UserDTO) || CollectionUtils.isEmpty(((UserDTO) user).getAccountList())) {
            LOGGER.warn("save user, but user type not correct...");
            return 0;
        }
        Account account = ((UserDTO) user).getAccountList().get(0);
        UserDTO userDTO = (UserDTO) user;
        int result = userMapper.insert(userDTO);
        if (result > 0) {
            result = accountMapper.insert(account);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(User user) {

        if (null == user) {
            LOGGER.warn("delete user, but user is null  or user id is null...");
            return 0;
        }

        return userMapper.deleteById(user);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(User user) {

        if (null == user) {
            LOGGER.warn("update user, but user is null  or user id is null...");
            return 0;
        }

        return userMapper.updateById(user);
    }

    @Override
    public User selectOne(User user) {
        if( user == null) {
            LOGGER.warn("select user one, but user is null ...");
            return null;
        }
        user = userMapper.selectOne( user );
        return user;
    }


//    @Override
//    public PagingData<User> selectPage(User user) {
//        PagingData<User> pagingData = new PagingData<User>();
//
//        if( null == user ) {
//            LOGGER.warn("select user page, but user is null...");
//            return pagingData;
//        }
//
//        Integer queryCount = userMapper.selectByIndexCount( user );
//        pagingData.setTotal( queryCount );
//
//        if( null != queryCount && queryCount <= 0 ) {
//            LOGGER.info("select user page , but count {} == 0 ...",queryCount);
//            return pagingData;
//        }
//
//        List<User> users =  selectByIndex( user );
//        pagingData.setRows(users);
//        return pagingData;
//    }
//
//    @Override
//    public List<User> selectByIndex(User user) {
//        List<User> users = new ArrayList<User>();
//        if( user == null) {
//            LOGGER.warn("select user by index, but user is null ...");
//            return users;
//        }
//
//        users = userMapper.selectByIndex( user );
//
//        return users;
//    }
}