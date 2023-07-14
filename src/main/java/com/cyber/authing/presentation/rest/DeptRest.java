package com.cyber.authing.presentation.rest;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;


import cn.hutool.core.lang.tree.Tree;
import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateDeptRequest;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.Dept;
import com.cyber.authing.domain.request.DeptRequest;
import com.cyber.authing.domain.request.CreateDeptRequest;

import com.cyber.authing.application.service.DeptService;

@RestController
@RequiredArgsConstructor
public class DeptRest extends AuthingTokenController{

	private final DeptService deptService;

	@GetMapping("/dept/search")
	public Response searchDept(@Valid DeptRequest request) {
		DataResponse<PagingData<Dept>> response = new DataResponse<>();
        Dept  dept = request.toEvent(request.getTenantCode());
		PagingData<Dept> deptPage = deptService.selectPage(dept);
		response.setData(deptPage);
		return response;
	}

	@GetMapping("/dept/tree")
	public Response selectDept(@Valid DeptRequest request) {
		DataResponse<List<Tree<String>>> response = new DataResponse<>();
        Dept  dept = request.toEvent(request.getTenantCode());
		List<Tree<String>> deptTree = deptService.selectTree(dept);
		response.setData(deptTree);
		return response;
	}

	@GetMapping("/dept/enterprise/tree")
	public Response selectEnterpriseDept(@Valid DeptRequest request) {
		DataResponse<List<Tree<String>>> response = new DataResponse<>();
        Dept  dept = request.toEvent(request.getTenantCode());
		List<Tree<String>> enterpriseTree = deptService.selectEnterpriseTree(dept);
		response.setData(enterpriseTree);
		return response;
	}

	@GetMapping("/dept/status/count")
	public Response selectEnterpriseStatusCount() {
		DataResponse<List<CountStatus>> response = new DataResponse<>();
		List<CountStatus> countStatus = deptService.countStatus();
		response.setData(countStatus);
		return response;
	}


	@GetMapping("/dept")
	public Response selectOneDept(@Valid IdRequest idRequest) {
		DataResponse<Dept> response = new DataResponse<>();

		Dept dept = new Dept();
		dept.setId(idRequest.getId());
        dept.setTenantCode(idRequest.getTenantCode());
		dept = deptService.selectOne(dept);

		response.setData(dept);
		return response;
	}

	@PostMapping("/dept")
	public Response saveDept(@RequestBody @Valid CreateDeptRequest request) {
	    Dept  dept = request.toEvent(getSessionId(),request.getTenantCode());

		int result = deptService.save(dept);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/dept")
	public Response updateDept(@RequestBody @Valid UpdateDeptRequest request) {
	    Dept  dept = request.toEvent(getSessionId(),request.getTenantCode());
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
		dept.setUpdator(getSessionId());
        dept.setUpdateTime(new Date());

		int result = deptService.deleteById(dept);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
