package com.cyber.authing.presentation.rest;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdatePositionRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.Position;
import com.cyber.authing.domain.request.PositionRequest;
import com.cyber.authing.domain.request.CreatePositionRequest;

import com.cyber.authing.application.PositionService;

@RestController
@RequiredArgsConstructor
public class PositionRest extends AuthingTokenController{

	private final PositionService positionService;

	@GetMapping("/position/search")
	public Response searchPosition(@Valid PositionRequest request) {
		DataResponse<PagingData<Position>> response = new DataResponse<>();
        Position  position = request.toEvent(request.getTenantCode());
		PagingData<Position> positionPage = positionService.selectPage(position);
		response.setData(positionPage);
		return response;
	}

	@GetMapping("/position/select")
	public Response selectPosition(@Valid PositionRequest request) {
		DataResponse<List<Position>> response = new DataResponse<>();
        Position  position = request.toEvent(request.getTenantCode());
		List<Position> positionPage = positionService.selectList(position);
		response.setData(positionPage);
		return response;
	}


	@GetMapping("/position")
	public Response selectOnePosition(@Valid IdRequest idRequest) {
		DataResponse<Position> response = new DataResponse<>();

		Position position = new Position();
		position.setId(idRequest.getId());
        position.setTenantCode(idRequest.getTenantCode());
		position = positionService.selectOne(position);

		response.setData(position);
		return response;
	}

	@PostMapping("/position")
	public Response savePosition(@RequestBody @Valid CreatePositionRequest request) {
	    Position  position = request.toEvent(getSessionId(),request.getTenantCode());

		int result = positionService.save(position);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/position")
	public Response updatePosition(@RequestBody @Valid UpdatePositionRequest request) {
	    Position  position = request.toEvent(getSessionId(),request.getTenantCode());
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
		position.setUpdator(getSessionId());
        position.setUpdateTime(new Date());

		int result = positionService.deleteById(position);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
