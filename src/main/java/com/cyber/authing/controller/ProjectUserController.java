package com.cyber.authing.controller;

import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.ProjectUser;
import com.cyber.authing.entity.request.CreateProjectUserRequest;
import com.cyber.authing.entity.request.ProjectUserRequest;
import com.cyber.authing.entity.request.UpdateProjectUserRequest;
import com.cyber.authing.service.ProjectUserService;
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
public class ProjectUserController {

    @Autowired
    ProjectUserService projectUserService;

    @GetMapping("/projectuser/search")
    public Response searchProjectUser(@Valid ProjectUserRequest request) {
        ProjectUser projectuser = request.toEvent(request.getTenantCode());
        return projectUserService.selectPage(projectuser);
    }

    @PostMapping("/projectuser")
    public Response saveProjectUser(@RequestBody @Valid CreateProjectUserRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        ProjectUser projectuser = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        List<ProjectUser> projectUserList = new ArrayList<>();
        request.getUserIdList().forEach(userId -> {
            ProjectUser projectUser = new ProjectUser();
            BeanUtils.copyProperties(projectuser, projectUser);
            projectUser.setUserId(userId);
            projectUserList.add(projectUser);
        });
        int result = projectUserService.batchSaveUpdate(projectUserList);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/projectuser")
    public Response updateProjectUser(@RequestBody @Valid UpdateProjectUserRequest request) {
        ProjectUser projectuser = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = projectUserService.updateById(projectuser);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/projectuser")
    public Response deleteProjectUser(List<Long> ids) {
        int result = projectUserService.deleteByIds(ids);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
