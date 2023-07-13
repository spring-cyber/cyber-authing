package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.UserDept;
import com.cyber.authing.domain.request.UserDeptRequest;
import com.cyber.authing.domain.request.CreateUserDeptRequest;
import com.cyber.authing.domain.request.UpdateUserDeptRequest;

import com.cyber.authing.application.UserDeptService;

@RestController
@RequiredArgsConstructor
public class UserDeptRest extends AuthingTokenController{

	private final UserDeptService userDeptService;

	@GetMapping("/userdept/search")
	public Response searchUserDept(@Valid UserDeptRequest request) {
		DataResponse<PagingData<UserDept>> response = new DataResponse<>();
        UserDept  userdept = request.toEvent(request.getTenantCode());
		PagingData<UserDept> userDeptPage = userDeptService.selectPage(userdept);
		response.setData(userDeptPage);
		return response;
	}


	@GetMapping("/userdept")
	public Response selectOneUserDept(@Valid IdRequest idRequest) {
		DataResponse<UserDept> response = new DataResponse<>();

		UserDept userDept = new UserDept();
		userDept.setId(idRequest.getId());
        userDept.setTenantCode(idRequest.getTenantCode());
		userDept = userDeptService.selectOne(userDept);

		response.setData(userDept);
		return response;
	}

	@PostMapping("/userdept")
	public Response saveUserDept(@RequestBody @Valid CreateUserDeptRequest request) {
	    UserDept  userdept = request.toEvent(getSessionId(),request.getTenantCode());

		int result = userDeptService.save(userdept);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/userdept")
	public Response updateUserDept(@RequestBody @Valid UpdateUserDeptRequest request) {
	    UserDept  userdept = request.toEvent(getSessionId(),request.getTenantCode());
		int result = userDeptService.updateById(userdept);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/userdept")
	public Response deleteUserDept(@Valid IdRequest idRequest) {
		UserDept userDept = new UserDept();
		userDept.setId(idRequest.getId());

		userDept.setTenantCode(idRequest.getTenantCode());
		userDept.setUpdator(getSessionId());
        userDept.setUpdateTime(new Date());

		int result = userDeptService.deleteById(userDept);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
