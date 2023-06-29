package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.UserRole;
import com.cyber.authing.entity.request.CreateUserRoleRequest;
import com.cyber.authing.entity.request.UserRoleRequest;
import com.cyber.authing.service.UserRoleService;
import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.entity.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/userrole/search")
    public Response searchUserRole(@Valid UserRoleRequest request) {
        UserRole userrole = request.toEvent(request.getTenantCode());
        return userRoleService.selectPage(userrole);
    }

    @PostMapping("/userrole")
    public Response saveUserRole(@RequestBody @Valid CreateUserRoleRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        UserRole userrole = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        List<UserRole> userRoleList = new ArrayList<>();
        request.getRoleIds().forEach(roleId -> {
            UserRole userRole = new UserRole();
            BeanUtils.copyProperties(userrole, userRole);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        });
        int result = userRoleService.batchSave(userRoleList);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/userrole")
    public Response deleteUserRole(List<Long> ids) {
        int result = userRoleService.deleteByIds(ids);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
