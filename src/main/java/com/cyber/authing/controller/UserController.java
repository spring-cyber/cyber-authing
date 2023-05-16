package com.cyber.authing.controller;

import com.cyber.authing.entity.domain.Account;
import com.cyber.authing.entity.domain.User;
import com.cyber.authing.entity.dto.UserDTO;
import com.cyber.authing.entity.request.CreateUserRequest;
import com.cyber.authing.entity.request.UpdateUserRequest;
import com.cyber.authing.entity.request.UserRequest;
import com.cyber.authing.mapper.UserMapper;
import com.cyber.authing.service.UserService;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.Response;
import com.cyber.mybatiesplus.core.query.QueryWrapperX;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @PostMapping("/register")
    public Response register(@RequestBody @Valid CreateUserRequest request) {
        User user = request.toEvent(getUserId());
        UserDTO userDTO = new UserDTO(user);
        Account account = Account.builder().password(request.getPassword()).userId(user.getId()).build();
        String name = request.getName();
        // 注册类型 1：手机注册 2：邮箱注册 3：账号注册
        if (1 == request.getRegisterType()) {
            Assert.notNull(request.getPhoneNumber(), "手册号码不能为空");
            account.setAccount(request.getPhoneNumber());
            name = StringUtils.isBlank(name) ? request.getPhoneNumber() : name;
        } else if (2 == request.getRegisterType()) {
            Assert.notNull(request.getEmail(), "邮箱不能为空");
            account.setAccount(request.getEmail());
            name = StringUtils.isBlank(name) ? request.getEmail() : name;
        } else {
            Assert.notNull(name, "用户名不能为空");
            account.setAccount(name);
        }
        user.setName(name);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        userDTO.setAccountList(accountList);
        userService.save(userDTO);
        return new Response();
    }

    @PostMapping("/user")
    public Response saveUser(@RequestBody @Valid CreateUserRequest request) {
        User user = request.toEvent(getUserId());
        userService.save(user);
        return new Response();
    }

    @PutMapping("/user")
    public Response updateUser(@RequestBody @Valid UpdateUserRequest request) {
        User user = request.toEvent(getUserId());
        userService.updateById(user);
        return new Response();
    }

    @DeleteMapping("/user")
    public Response deleteUser(@Valid IdRequest idRequest) {
        User user = new User();
        user.setId(idRequest.getId());
        user.setUpdator(getUserId());
        user.setUpdateTime(new Date());
        userService.deleteById(user);
        return new Response();
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('/user')")
    public Response getUser(UserRequest userRequest) {
        User user = userRequest.toEvent(null);
        QueryWrapperX queryWrapperX = new QueryWrapperX<>().likeIfPresent("name", userRequest.getName());
        return userMapper.selectPage(userRequest, queryWrapperX);
    }

    private String getUserId() {
        return "test";
    }
}
