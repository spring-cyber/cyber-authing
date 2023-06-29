package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.Project;
import com.cyber.authing.entity.request.CreateProjectRequest;
import com.cyber.authing.entity.request.ProjectRequest;
import com.cyber.authing.entity.request.UpdateProjectRequest;
import com.cyber.authing.service.ProjectService;
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
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/project/search")
    public Response searchProject(@Valid ProjectRequest request) {
        Project project = request.toEvent(request.getTenantCode());
        return projectService.selectPage(project);
    }

    @GetMapping("/project")
    public Response selectOneProject(@Valid IdRequest idRequest) {
        DataResponse<Project> response = new DataResponse<Project>();
        Project project = new Project();
        project.setId(idRequest.getId());
        project.setTenantCode(idRequest.getTenantCode());
        project = projectService.selectOne(project);
        response.setData(project);
        return response;
    }

    @PostMapping("/project")
    public Response saveProject(@RequestBody @Valid CreateProjectRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        Project project = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = projectService.save(project);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/project")
    public Response updateProject(@RequestBody @Valid UpdateProjectRequest request) {
        Project project = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = projectService.updateById(project);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/project")
    public Response deleteProject(@Valid IdRequest idRequest) {
        Project project = new Project();
        project.setId(idRequest.getId());
        project.setTenantCode(idRequest.getTenantCode());
        project.setUpdator(AuthenticationUtil.getUserCode());
        project.setUpdateTime(new Date());
        int result = projectService.deleteById(project);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
