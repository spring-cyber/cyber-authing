package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateAccessConfigRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.AccessConfig;
import com.cyber.authing.domain.request.AccessConfigRequest;
import com.cyber.authing.domain.request.CreateAccessConfigRequest;

import com.cyber.authing.application.service.AccessConfigService;

@RestController
@RequiredArgsConstructor
public class AccessConfigRest extends AuthingTokenController{

	private final AccessConfigService accessConfigService;

	@GetMapping("/accessconfig/search")
	public Response searchAccessConfig(@Valid AccessConfigRequest request) {
		DataResponse<PagingData<AccessConfig>> response = new DataResponse<>();
        AccessConfig  accessconfig = request.toEvent(request.getTenantCode());
		PagingData<AccessConfig> accessConfigPage = accessConfigService.selectPage(accessconfig);
		response.setData(accessConfigPage);
		return response;
	}


	@GetMapping("/accessconfig")
	public Response selectOneAccessConfig(@Valid IdRequest idRequest) {
		DataResponse<AccessConfig> response = new DataResponse<>();

		AccessConfig accessConfig = new AccessConfig();
		accessConfig.setId(idRequest.getId());
        accessConfig.setTenantCode(idRequest.getTenantCode());
		accessConfig = accessConfigService.selectOne(accessConfig);

		response.setData(accessConfig);
		return response;
	}

	@PostMapping("/accessconfig")
	public Response saveAccessConfig(@RequestBody @Valid CreateAccessConfigRequest request) {
	    AccessConfig  accessconfig = request.toEvent(getSessionId(),request.getTenantCode());

		int result = accessConfigService.save(accessconfig);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/accessconfig")
	public Response updateAccessConfig(@RequestBody @Valid UpdateAccessConfigRequest request) {
	    AccessConfig  accessconfig = request.toEvent(getSessionId(),request.getTenantCode());
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

		accessConfig.setTenantCode(idRequest.getTenantCode());
		accessConfig.setUpdator(getSessionId());
        accessConfig.setUpdateTime(new Date());

		int result = accessConfigService.deleteById(accessConfig);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
