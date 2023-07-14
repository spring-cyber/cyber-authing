package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateRoleRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.Role;
import com.cyber.authing.domain.request.RoleRequest;
import com.cyber.authing.domain.request.CreateRoleRequest;

import com.cyber.authing.application.service.RoleService;

@RestController
@RequiredArgsConstructor
public class RoleRest extends AuthingTokenController{

	private final RoleService roleService;

	@GetMapping("/role/search")
	public Response searchRole(@Valid RoleRequest request) {
		DataResponse<PagingData<Role>> response = new DataResponse<>();
        Role  role = request.toEvent(request.getTenantCode());
		PagingData<Role> rolePage = roleService.selectPage(role);
		response.setData(rolePage);
		return response;
	}


	@GetMapping("/role")
	public Response selectOneRole(@Valid IdRequest idRequest) {
		DataResponse<Role> response = new DataResponse<>();

		Role role = new Role();
		role.setId(idRequest.getId());
        role.setTenantCode(idRequest.getTenantCode());
		role = roleService.selectOne(role);

		response.setData(role);
		return response;
	}

	@PostMapping("/role")
	public Response saveRole(@RequestBody @Valid CreateRoleRequest request) {
	    Role  role = request.toEvent(getSessionId(),request.getTenantCode());

		int result = roleService.save(role);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/role")
	public Response updateRole(@RequestBody @Valid UpdateRoleRequest request) {
	    Role  role = request.toEvent(getSessionId(),request.getTenantCode());
		int result = roleService.updateById(role);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/role")
	public Response deleteRole(@Valid IdRequest idRequest) {
		Role role = new Role();
		role.setId(idRequest.getId());

		role.setTenantCode(idRequest.getTenantCode());
		role.setUpdator(getSessionId());
        role.setUpdateTime(new Date());

		int result = roleService.deleteById(role);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
