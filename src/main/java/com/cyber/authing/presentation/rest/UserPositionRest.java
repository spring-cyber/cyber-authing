package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateUserPositionRequest;
import com.cyber.authing.domain.request.UserPositionRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.UserPosition;
import com.cyber.authing.domain.request.CreateUserPositionRequest;

import com.cyber.authing.application.service.UserPositionService;

@RestController
@RequiredArgsConstructor
public class UserPositionRest extends AuthingTokenController{

	private final UserPositionService userPositionService;

	@GetMapping("/userposition/search")
	public Response searchUserPosition(@Valid UserPositionRequest request) {
		DataResponse<PagingData<UserPosition>> response = new DataResponse<>();
        UserPosition  userposition = request.toEvent(request.getTenantCode());
		PagingData<UserPosition> userPositionPage = userPositionService.selectPage(userposition);
		response.setData(userPositionPage);
		return response;
	}


	@GetMapping("/userposition")
	public Response selectOneUserPosition(@Valid IdRequest idRequest) {
		DataResponse<UserPosition> response = new DataResponse<>();

		UserPosition userPosition = new UserPosition();
		userPosition.setId(idRequest.getId());
        userPosition.setTenantCode(idRequest.getTenantCode());
		userPosition = userPositionService.selectOne(userPosition);

		response.setData(userPosition);
		return response;
	}

	@PostMapping("/userposition")
	public Response saveUserPosition(@RequestBody @Valid CreateUserPositionRequest request) {
	    UserPosition  userposition = request.toEvent(getSessionId(),request.getTenantCode());

		int result = userPositionService.save(userposition);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/userposition")
	public Response updateUserPosition(@RequestBody @Valid UpdateUserPositionRequest request) {
	    UserPosition  userposition = request.toEvent(getSessionId(),request.getTenantCode());
		int result = userPositionService.updateById(userposition);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/userposition")
	public Response deleteUserPosition(@Valid IdRequest idRequest) {
		UserPosition userPosition = new UserPosition();
		userPosition.setId(idRequest.getId());

		userPosition.setTenantCode(idRequest.getTenantCode());
		userPosition.setUpdator(getSessionId());
        userPosition.setUpdateTime(new Date());

		int result = userPositionService.deleteById(userPosition);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
