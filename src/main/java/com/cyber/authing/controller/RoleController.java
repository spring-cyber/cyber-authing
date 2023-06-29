package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.Permission;
import com.cyber.authing.entity.domain.Role;
import com.cyber.authing.entity.request.CreateRoleRequest;
import com.cyber.authing.entity.request.RoleRequest;
import com.cyber.authing.entity.request.UpdateRoleRequest;
import com.cyber.authing.service.PermissionService;
import com.cyber.authing.service.RoleService;
import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    @GetMapping("/role/search")
    public Response searchRole(@Valid RoleRequest request) {
        Role role = request.toEvent(request.getTenantCode());
        return roleService.selectPage(role);
    }

    @GetMapping("/role")
    public Response selectOneRole(@Valid IdRequest idRequest) {
        DataResponse<Role> response = new DataResponse<Role>();
        Role role = new Role();
        role.setId(idRequest.getId());
        role.setTenantCode(idRequest.getTenantCode());
        role = roleService.selectOne(role);
        response.setData(role);
        return response;
    }

    @PostMapping("/role")
    public Response saveRole(@RequestBody @Valid CreateRoleRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        Role role = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        // 处理权限
        if (!CollectionUtils.isEmpty(request.getPermissionIds())) {
            List<Permission> permissionList = permissionService.selectByIds(request.getPermissionIds());
            role.setPermissionList(permissionList);
        }
        int result = roleService.save(role);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/role")
    public Response updateRole(@RequestBody @Valid UpdateRoleRequest request) {
        Role role = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = roleService.updateById(role);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/role")
    public Response deleteRole(@Valid IdRequest idRequest) {
        Role role = new Role();
        role.setId(idRequest.getId());
        role.setTenantCode(idRequest.getTenantCode());
        role.setUpdator(AuthenticationUtil.getUserCode());
        role.setUpdateTime(new Date());
        int result = roleService.deleteById(role);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
