package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.Tenant;
import com.cyber.authing.entity.request.CreateTenantRequest;
import com.cyber.authing.entity.request.TenantRequest;
import com.cyber.authing.entity.request.UpdateTenantRequest;
import com.cyber.authing.service.TenantService;
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
public class TenantController {

    @Autowired
    TenantService tenantService;

    @GetMapping("/tenant/search")
    public Response searchTenant(@Valid TenantRequest request) {
        Tenant tenant = request.toEvent(request.getTenantCode());
        return tenantService.selectPage(tenant);
    }


    @GetMapping("/tenant")
    public Response selectOneTenant(@Valid IdRequest idRequest) {
        DataResponse<Tenant> response = new DataResponse<Tenant>();
        Tenant tenant = new Tenant();
        tenant.setId(idRequest.getId());
        tenant.setTenantCode(idRequest.getTenantCode());
        tenant = tenantService.selectOne(tenant);
        response.setData(tenant);
        return response;
    }

    @PostMapping("/tenant")
    public Response saveTenant(@RequestBody @Valid CreateTenantRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        Tenant tenant = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = tenantService.save(tenant);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/tenant")
    public Response updateTenant(@RequestBody @Valid UpdateTenantRequest request) {
        Tenant tenant = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = tenantService.updateById(tenant);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/tenant")
    public Response deleteTenant(@Valid IdRequest idRequest) {
        Tenant tenant = new Tenant();
        tenant.setId(idRequest.getId());
        tenant.setTenantCode(idRequest.getTenantCode());
        tenant.setUpdator(AuthenticationUtil.getUserCode());
        tenant.setUpdateTime(new Date());
        int result = tenantService.deleteById(tenant);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
