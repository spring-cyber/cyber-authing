package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.Dept;
import com.cyber.authing.entity.domain.DeptUser;
import com.cyber.authing.entity.request.*;
import com.cyber.authing.service.DeptService;
import com.cyber.authing.service.DeptUserService;
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
import java.util.List;

@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class DeptController {

    @Autowired
    DeptService deptService;
    @Autowired
    DeptUserService deptUserService;

    @GetMapping("/dept/search")
    public Response searchDept(@Valid DeptRequest request) {
        Dept dept = request.toEvent(request.getTenantCode());
        return deptService.selectPage(dept);
    }

    @GetMapping("/dept")
    public Response selectOneDept(@Valid IdRequest idRequest) {
        DataResponse<Dept> response = new DataResponse<Dept>();
        Dept dept = new Dept();
        dept.setId(idRequest.getId());
        dept.setTenantCode(idRequest.getTenantCode());
        dept = deptService.selectOne(dept);
        response.setData(dept);
        return response;
    }

    @PostMapping("/dept")
    public Response saveDept(@RequestBody @Valid CreateDeptRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        Dept dept = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = deptService.save(dept);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/dept")
    public Response updateDept(@RequestBody @Valid UpdateDeptRequest request) {
        Dept dept = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = deptService.updateById(dept);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/dept")
    public Response deleteDept(@Valid IdRequest idRequest) {
        Dept dept = new Dept();
        dept.setId(idRequest.getId());
        dept.setTenantCode(idRequest.getTenantCode());
        dept.setUpdator(AuthenticationUtil.getUserCode());
        dept.setUpdateTime(new Date());
        int result = deptService.deleteById(dept);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @GetMapping("/deptuser/search")
    public Response searchDeptUser(@Valid DeptUserRequest request) {
        DeptUser deptuser = request.toEvent(request.getTenantCode());
        return deptUserService.selectPage(deptuser);
    }

    @PostMapping("/deptuser")
    public Response saveDeptUser(@RequestBody @Valid CreateDeptUserRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        DeptUser deptuser = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = deptUserService.save(deptuser);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/deptuser")
    public Response updateDeptUser(@RequestBody @Valid UpdateDeptUserRequest request) {
        DeptUser deptuser = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = deptUserService.updateById(deptuser);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/deptuser")
    public Response deleteDeptUser(List<Long> ids) {
        int result = deptUserService.deleteByIds(ids);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
