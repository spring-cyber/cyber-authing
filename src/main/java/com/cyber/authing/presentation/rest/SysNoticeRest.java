package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateSysNoticeRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.SysNotice;
import com.cyber.authing.domain.request.SysNoticeRequest;
import com.cyber.authing.domain.request.CreateSysNoticeRequest;

import com.cyber.authing.application.service.SysNoticeService;

@RestController
@RequiredArgsConstructor
public class SysNoticeRest extends AuthingTokenController{

	private final SysNoticeService sysNoticeService;

	@GetMapping("/sysnotice/search")
	public Response searchSysNotice(@Valid SysNoticeRequest request) {
		DataResponse<PagingData<SysNotice>> response = new DataResponse<>();
        SysNotice  sysnotice = request.toEvent(request.getTenantCode());
		PagingData<SysNotice> sysNoticePage = sysNoticeService.selectPage(sysnotice);
		response.setData(sysNoticePage);
		return response;
	}


	@GetMapping("/sysnotice")
	public Response selectOneSysNotice(@Valid IdRequest idRequest) {
		DataResponse<SysNotice> response = new DataResponse<>();

		SysNotice sysNotice = new SysNotice();
		sysNotice.setId(idRequest.getId());
        sysNotice.setTenantCode(idRequest.getTenantCode());
		sysNotice = sysNoticeService.selectOne(sysNotice);

		response.setData(sysNotice);
		return response;
	}

	@PostMapping("/sysnotice")
	public Response saveSysNotice(@RequestBody @Valid CreateSysNoticeRequest request) {
	    SysNotice  sysnotice = request.toEvent(getSessionId(),request.getTenantCode());

		int result = sysNoticeService.save(sysnotice);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/sysnotice")
	public Response updateSysNotice(@RequestBody @Valid UpdateSysNoticeRequest request) {
	    SysNotice  sysnotice = request.toEvent(getSessionId(),request.getTenantCode());
		int result = sysNoticeService.updateById(sysnotice);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/sysnotice")
	public Response deleteSysNotice(@Valid IdRequest idRequest) {
		SysNotice sysNotice = new SysNotice();
		sysNotice.setId(idRequest.getId());

		sysNotice.setTenantCode(idRequest.getTenantCode());
		sysNotice.setUpdator(getSessionId());
        sysNotice.setUpdateTime(new Date());

		int result = sysNoticeService.deleteById(sysNotice);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
