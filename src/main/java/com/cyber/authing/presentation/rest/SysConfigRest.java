package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateSysConfigRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.SysConfig;
import com.cyber.authing.domain.request.SysConfigRequest;
import com.cyber.authing.domain.request.CreateSysConfigRequest;

import com.cyber.authing.application.SysConfigService;

@RestController
@RequiredArgsConstructor
public class SysConfigRest extends AuthingTokenController{

	private final SysConfigService sysConfigService;

	@GetMapping("/sysconfig/search")
	public Response searchSysConfig(@Valid SysConfigRequest request) {
		DataResponse<PagingData<SysConfig>> response = new DataResponse<>();
        SysConfig  sysconfig = request.toEvent(request.getTenantCode());
		PagingData<SysConfig> sysConfigPage = sysConfigService.selectPage(sysconfig);
		response.setData(sysConfigPage);
		return response;
	}


	@GetMapping("/sysconfig")
	public Response selectOneSysConfig(@Valid IdRequest idRequest) {
		DataResponse<SysConfig> response = new DataResponse<>();

		SysConfig sysConfig = new SysConfig();
		sysConfig.setId(idRequest.getId());
        sysConfig.setTenantCode(idRequest.getTenantCode());
		sysConfig = sysConfigService.selectOne(sysConfig);

		response.setData(sysConfig);
		return response;
	}

	@PostMapping("/sysconfig")
	public Response saveSysConfig(@RequestBody @Valid CreateSysConfigRequest request) {
	    SysConfig  sysconfig = request.toEvent(getSessionId(),request.getTenantCode());

		int result = sysConfigService.save(sysconfig);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/sysconfig")
	public Response updateSysConfig(@RequestBody @Valid UpdateSysConfigRequest request) {
	    SysConfig  sysconfig = request.toEvent(getSessionId(),request.getTenantCode());
		int result = sysConfigService.updateById(sysconfig);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/sysconfig")
	public Response deleteSysConfig(@Valid IdRequest idRequest) {
		SysConfig sysConfig = new SysConfig();
		sysConfig.setId(idRequest.getId());

		sysConfig.setTenantCode(idRequest.getTenantCode());
		sysConfig.setUpdator(getSessionId());
        sysConfig.setUpdateTime(new Date());

		int result = sysConfigService.deleteById(sysConfig);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
