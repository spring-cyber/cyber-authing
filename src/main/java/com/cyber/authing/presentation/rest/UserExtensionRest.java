package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateUserExtensionRequest;
import com.cyber.authing.domain.request.UserExtensionRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.UserExtension;
import com.cyber.authing.domain.request.CreateUserExtensionRequest;

import com.cyber.authing.application.UserExtensionService;

@RestController
@RequiredArgsConstructor
public class UserExtensionRest extends AuthingTokenController{

	private final UserExtensionService userExtensionService;

	@GetMapping("/userextension/search")
	public Response searchUserExtension(@Valid UserExtensionRequest request) {
		DataResponse<PagingData<UserExtension>> response = new DataResponse<>();
        UserExtension  userextension = request.toEvent(request.getTenantCode());
		PagingData<UserExtension> userExtensionPage = userExtensionService.selectPage(userextension);
		response.setData(userExtensionPage);
		return response;
	}


	@GetMapping("/userextension")
	public Response selectOneUserExtension(@Valid IdRequest idRequest) {
		DataResponse<UserExtension> response = new DataResponse<>();

		UserExtension userExtension = new UserExtension();
		userExtension.setId(idRequest.getId());
        userExtension.setTenantCode(idRequest.getTenantCode());
		userExtension = userExtensionService.selectOne(userExtension);

		response.setData(userExtension);
		return response;
	}

	@PostMapping("/userextension")
	public Response saveUserExtension(@RequestBody @Valid CreateUserExtensionRequest request) {
	    UserExtension  userextension = request.toEvent(getSessionId(),request.getTenantCode());

		int result = userExtensionService.save(userextension);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/userextension")
	public Response updateUserExtension(@RequestBody @Valid UpdateUserExtensionRequest request) {
	    UserExtension  userextension = request.toEvent(getSessionId(),request.getTenantCode());
		int result = userExtensionService.updateById(userextension);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/userextension")
	public Response deleteUserExtension(@Valid IdRequest idRequest) {
		UserExtension userExtension = new UserExtension();
		userExtension.setId(idRequest.getId());

		userExtension.setTenantCode(idRequest.getTenantCode());
		userExtension.setUpdator(getSessionId());
        userExtension.setUpdateTime(new Date());

		int result = userExtensionService.deleteById(userExtension);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
