package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.Permission;
import com.cyber.authing.entity.request.CreatePermissionRequest;
import com.cyber.authing.entity.request.PermissionRequest;
import com.cyber.authing.entity.request.UpdatePermissionRequest;
import com.cyber.authing.service.PermissionService;
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
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping("/permission/search")
    public Response searchPermission(@Valid PermissionRequest request) {
        Permission permission = request.toEvent(request.getTenantCode());
        return permissionService.selectPage(permission);
    }


    @GetMapping("/permission")
    public Response selectOnePermission(@Valid IdRequest idRequest) {
        DataResponse<Permission> response = new DataResponse<Permission>();
        Permission permission = new Permission();
        permission.setId(idRequest.getId());
        permission.setTenantCode(idRequest.getTenantCode());
        permission = permissionService.selectOne(permission);
        response.setData(permission);
        return response;
    }

    @PostMapping("/permission")
    public Response savePermission(@RequestBody @Valid CreatePermissionRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        Permission permission = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = permissionService.save(permission);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/permission")
    public Response updatePermission(@RequestBody @Valid UpdatePermissionRequest request) {
        Permission permission = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = permissionService.updateById(permission);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/permission")
    public Response deletePermission(@Valid IdRequest idRequest) {
        Permission permission = new Permission();
        permission.setId(idRequest.getId());
        permission.setTenantCode(idRequest.getTenantCode());
        permission.setUpdator(AuthenticationUtil.getUserCode());
        permission.setUpdateTime(new Date());
        int result = permissionService.deleteById(permission);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
