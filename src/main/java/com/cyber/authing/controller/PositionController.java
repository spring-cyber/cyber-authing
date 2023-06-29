package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.Position;
import com.cyber.authing.entity.request.CreatePositionRequest;
import com.cyber.authing.entity.request.PositionRequest;
import com.cyber.authing.entity.request.UpdatePositionRequest;
import com.cyber.authing.service.PositionService;
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
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping("/position/search")
    public Response searchPosition(@Valid PositionRequest request) {
        Position position = request.toEvent(request.getTenantCode());
        return positionService.selectPage(position);
    }

    @GetMapping("/position")
    public Response selectOnePosition(@Valid IdRequest idRequest) {
        DataResponse<Position> response = new DataResponse<Position>();
        Position position = new Position();
        position.setId(idRequest.getId());
        position.setTenantCode(idRequest.getTenantCode());
        position = positionService.selectOne(position);
        response.setData(position);
        return response;
    }

    @PostMapping("/position")
    public Response savePosition(@RequestBody @Valid CreatePositionRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        Position position = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = positionService.save(position);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/position")
    public Response updatePosition(@RequestBody @Valid UpdatePositionRequest request) {
        Position position = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = positionService.updateById(position);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/position")
    public Response deletePosition(@Valid IdRequest idRequest) {
        Position position = new Position();
        position.setId(idRequest.getId());
        position.setTenantCode(idRequest.getTenantCode());
        position.setUpdator(AuthenticationUtil.getUserCode());
        position.setUpdateTime(new Date());
        int result = positionService.deleteById(position);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
