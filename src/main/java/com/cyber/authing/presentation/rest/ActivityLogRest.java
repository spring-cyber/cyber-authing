package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateActivityLogRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.ActivityLog;
import com.cyber.authing.domain.request.ActivityLogRequest;
import com.cyber.authing.domain.request.CreateActivityLogRequest;

import com.cyber.authing.application.ActivityLogService;

@RestController
@RequiredArgsConstructor
public class ActivityLogRest extends AuthingTokenController{

	private final ActivityLogService activityLogService;

	@GetMapping("/activitylog/search")
	public Response searchActivityLog(@Valid ActivityLogRequest request) {
		DataResponse<PagingData<ActivityLog>> response = new DataResponse<>();
        ActivityLog  activitylog = request.toEvent(request.getTenantCode());
		PagingData<ActivityLog> activityLogPage = activityLogService.selectPage(activitylog);
		response.setData(activityLogPage);
		return response;
	}


	@GetMapping("/activitylog")
	public Response selectOneActivityLog(@Valid IdRequest idRequest) {
		DataResponse<ActivityLog> response = new DataResponse<>();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setId(idRequest.getId());
        activityLog.setTenantCode(idRequest.getTenantCode());
		activityLog = activityLogService.selectOne(activityLog);

		response.setData(activityLog);
		return response;
	}

	@PostMapping("/activitylog")
	public Response saveActivityLog(@RequestBody @Valid CreateActivityLogRequest request) {
	    ActivityLog  activitylog = request.toEvent(getSessionId(),request.getTenantCode());

		int result = activityLogService.save(activitylog);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/activitylog")
	public Response updateActivityLog(@RequestBody @Valid UpdateActivityLogRequest request) {
	    ActivityLog  activitylog = request.toEvent(getSessionId(),request.getTenantCode());
		int result = activityLogService.updateById(activitylog);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/activitylog")
	public Response deleteActivityLog(@Valid IdRequest idRequest) {
		ActivityLog activityLog = new ActivityLog();
		activityLog.setId(idRequest.getId());

		activityLog.setTenantCode(idRequest.getTenantCode());
		activityLog.setUpdator(getSessionId());
        activityLog.setUpdateTime(new Date());

		int result = activityLogService.deleteById(activityLog);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
