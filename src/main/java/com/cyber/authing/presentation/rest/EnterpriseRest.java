package com.cyber.authing.presentation.rest;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;


import cn.hutool.core.lang.tree.Tree;
import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateEnterpriseRequest;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.Enterprise;
import com.cyber.authing.domain.request.EnterpriseRequest;
import com.cyber.authing.domain.request.CreateEnterpriseRequest;

import com.cyber.authing.application.service.EnterpriseService;

@RestController
@RequiredArgsConstructor
public class EnterpriseRest extends AuthingTokenController{

	private final EnterpriseService enterpriseService;

	@GetMapping("/enterprise/search")
	public Response searchEnterprise(@Valid EnterpriseRequest request) {
		DataResponse<PagingData<Enterprise>> response = new DataResponse<>();
        Enterprise  enterprise = request.toEvent(request.getTenantCode());
		PagingData<Enterprise> enterprisePage = enterpriseService.selectPage(enterprise);
		response.setData(enterprisePage);
		return response;
	}

	@GetMapping("/enterprise/select")
	public Response selectEnterprise(@Valid EnterpriseRequest request) {
		DataResponse<List<Enterprise>> response = new DataResponse<>();
        Enterprise  enterprise = request.toEvent(request.getTenantCode());
		List<Enterprise> enterprisePage = enterpriseService.selectList(enterprise);
		response.setData(enterprisePage);
		return response;
	}

	@GetMapping("/enterprise/user/tree")
	public Response selectEnterpriseUserTree(@Valid EnterpriseRequest request) {
		DataResponse<List<Tree<String>>> response = new DataResponse<>();
        Enterprise  enterprise = request.toEvent(request.getTenantCode());
		List<Tree<String>> enterpriseUserTree = enterpriseService.selectEnterpriseUserTree(enterprise);
		response.setData(enterpriseUserTree);
		return response;
	}

	@GetMapping("/enterprise/status/count")
	public Response selectEnterpriseStatusCount() {
		DataResponse<List<CountStatus>> response = new DataResponse<>();
		List<CountStatus> countStatus = enterpriseService.countStatus();
		response.setData(countStatus);
		return response;
	}


	@GetMapping("/enterprise")
	public Response selectOneEnterprise(@Valid IdRequest idRequest) {
		DataResponse<Enterprise> response = new DataResponse<>();

		Enterprise enterprise = new Enterprise();
		enterprise.setId(idRequest.getId());
        enterprise.setTenantCode(idRequest.getTenantCode());
		enterprise = enterpriseService.selectOne(enterprise);

		response.setData(enterprise);
		return response;
	}

	@PostMapping("/enterprise")
	public Response saveEnterprise(@RequestBody @Valid CreateEnterpriseRequest request) {
	    Enterprise  enterprise = request.toEvent(getSessionId(),request.getTenantCode());

		int result = enterpriseService.save(enterprise);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/enterprise")
	public Response updateEnterprise(@RequestBody @Valid UpdateEnterpriseRequest request) {
	    Enterprise  enterprise = request.toEvent(getSessionId(),request.getTenantCode());
		int result = enterpriseService.updateById(enterprise);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/enterprise")
	public Response deleteEnterprise(@Valid IdRequest idRequest) {
		Enterprise enterprise = new Enterprise();
		enterprise.setId(idRequest.getId());

		enterprise.setTenantCode(idRequest.getTenantCode());
		enterprise.setUpdator(getSessionId());
        enterprise.setUpdateTime(new Date());

		int result = enterpriseService.deleteById(enterprise);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
