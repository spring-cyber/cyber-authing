package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.Account;
import com.cyber.authing.entity.request.AccountRequest;
import com.cyber.authing.entity.request.CreateAccountRequest;
import com.cyber.authing.entity.request.UpdateAccountRequest;
import com.cyber.authing.service.AccountService;
import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;
import java.util.Date;

@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/account/search")
    public Response searchAccount(@Valid AccountRequest request) {
        Account account = request.toEvent(request.getTenantCode());
        return accountService.selectPage(account);
    }

    @GetMapping("/account")
    public Response selectOneAccount(@Valid IdRequest idRequest) {
        DataResponse<Account> response = new DataResponse<Account>();
        Account account = new Account();
        account.setId(idRequest.getId());
        account.setTenantCode(idRequest.getTenantCode());
        account = accountService.selectOne(account);
        response.setData(account);
        return response;
    }

    @PostMapping("/account")
    public Response saveAccount(@RequestBody @Valid CreateAccountRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        Account account = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = accountService.save(account);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/account")
    public Response updateAccount(@RequestBody @Valid UpdateAccountRequest request) {
        Account account = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = accountService.updateById(account);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/account")
    public Response deleteAccount(@Valid IdRequest idRequest) {
        Account account = new Account();
        account.setId(idRequest.getId());
        account.setTenantCode(idRequest.getTenantCode());
        account.setUpdator(AuthenticationUtil.getUserCode());
        account.setUpdateTime(new Date());
        int result = accountService.deleteById(account);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
