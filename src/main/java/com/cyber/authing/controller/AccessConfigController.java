package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.AccessConfig;
import com.cyber.authing.entity.request.AccessConfigRequest;
import com.cyber.authing.entity.request.CreateAccessConfigRequest;
import com.cyber.authing.entity.request.UpdateAccessConfigRequest;
import com.cyber.authing.service.AccessConfigService;
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
public class AccessConfigController {

    @Autowired
    AccessConfigService accessConfigService;

    @GetMapping("/accessconfig/search")
    public Response searchAccessConfig(@Valid AccessConfigRequest request) {
        AccessConfig accessconfig = request.toEvent(request.getTenantCode());
        return accessConfigService.selectPage(accessconfig);
    }


    @GetMapping("/accessconfig")
    public Response selectOneAccessConfig(@Valid IdRequest idRequest) {
        DataResponse<AccessConfig> response = new DataResponse<AccessConfig>();
        AccessConfig accessConfig = new AccessConfig();
        accessConfig.setId(idRequest.getId());
        accessConfig.setTenantCode(idRequest.getTenantCode());
        accessConfig = accessConfigService.selectOne(accessConfig);
        response.setData(accessConfig);
        return response;
    }

    @PostMapping("/accessconfig")
    public Response saveAccessConfig(@RequestBody @Valid CreateAccessConfigRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        AccessConfig accessconfig = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = accessConfigService.save(accessconfig);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/accessconfig")
    public Response updateAccessConfig(@RequestBody @Valid UpdateAccessConfigRequest request) {
        AccessConfig accessconfig = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = accessConfigService.updateById(accessconfig);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/accessconfig")
    public Response deleteAccessConfig(@Valid IdRequest idRequest) {
        AccessConfig accessConfig = new AccessConfig();
        accessConfig.setId(idRequest.getId());
        accessConfig.setUpdator(AuthenticationUtil.getUserCode());
        accessConfig.setUpdateTime(new Date());
        int result = accessConfigService.deleteById(accessConfig);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
