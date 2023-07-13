package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateUserRoleRequest;
import com.cyber.authing.domain.request.UserRoleRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.UserRole;
import com.cyber.authing.domain.request.CreateUserRoleRequest;

import com.cyber.authing.application.UserRoleService;

@RestController
@RequiredArgsConstructor
public class UserRoleRest extends AuthingTokenController{

	private final UserRoleService userRoleService;

	@GetMapping("/userrole/search")
	public Response searchUserRole(@Valid UserRoleRequest request) {
		DataResponse<PagingData<UserRole>> response = new DataResponse<>();
        UserRole  userrole = request.toEvent(request.getTenantCode());
		PagingData<UserRole> userRolePage = userRoleService.selectPage(userrole);
		response.setData(userRolePage);
		return response;
	}


	@GetMapping("/userrole")
	public Response selectOneUserRole(@Valid IdRequest idRequest) {
		DataResponse<UserRole> response = new DataResponse<>();

		UserRole userRole = new UserRole();
		userRole.setId(idRequest.getId());
        userRole.setTenantCode(idRequest.getTenantCode());
		userRole = userRoleService.selectOne(userRole);

		response.setData(userRole);
		return response;
	}

	@PostMapping("/userrole")
	public Response saveUserRole(@RequestBody @Valid CreateUserRoleRequest request) {
	    UserRole  userrole = request.toEvent(getSessionId(),request.getTenantCode());

		int result = userRoleService.save(userrole);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/userrole")
	public Response updateUserRole(@RequestBody @Valid UpdateUserRoleRequest request) {
	    UserRole  userrole = request.toEvent(getSessionId(),request.getTenantCode());
		int result = userRoleService.updateById(userrole);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/userrole")
	public Response deleteUserRole(@Valid IdRequest idRequest) {
		UserRole userRole = new UserRole();
		userRole.setId(idRequest.getId());

		userRole.setTenantCode(idRequest.getTenantCode());
		userRole.setUpdator(getSessionId());
        userRole.setUpdateTime(new Date());

		int result = userRoleService.deleteById(userRole);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
