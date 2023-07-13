package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateRoleMenuRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.RoleMenu;
import com.cyber.authing.domain.request.RoleMenuRequest;
import com.cyber.authing.domain.request.CreateRoleMenuRequest;

import com.cyber.authing.application.RoleMenuService;

@RestController
@RequiredArgsConstructor
public class RoleMenuRest extends AuthingTokenController{

	private final RoleMenuService roleMenuService;

	@GetMapping("/rolemenu/search")
	public Response searchRoleMenu(@Valid RoleMenuRequest request) {
		DataResponse<PagingData<RoleMenu>> response = new DataResponse<>();
        RoleMenu  rolemenu = request.toEvent(request.getTenantCode());
		PagingData<RoleMenu> roleMenuPage = roleMenuService.selectPage(rolemenu);
		response.setData(roleMenuPage);
		return response;
	}


	@GetMapping("/rolemenu")
	public Response selectOneRoleMenu(@Valid IdRequest idRequest) {
		DataResponse<RoleMenu> response = new DataResponse<>();

		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setId(idRequest.getId());
        roleMenu.setTenantCode(idRequest.getTenantCode());
		roleMenu = roleMenuService.selectOne(roleMenu);

		response.setData(roleMenu);
		return response;
	}

	@PostMapping("/rolemenu")
	public Response saveRoleMenu(@RequestBody @Valid CreateRoleMenuRequest request) {
	    RoleMenu  rolemenu = request.toEvent(getSessionId(),request.getTenantCode());

		int result = roleMenuService.save(rolemenu);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/rolemenu")
	public Response updateRoleMenu(@RequestBody @Valid UpdateRoleMenuRequest request) {
	    RoleMenu  rolemenu = request.toEvent(getSessionId(),request.getTenantCode());
		int result = roleMenuService.updateById(rolemenu);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/rolemenu")
	public Response deleteRoleMenu(@Valid IdRequest idRequest) {
		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setId(idRequest.getId());

		roleMenu.setTenantCode(idRequest.getTenantCode());
		roleMenu.setUpdator(getSessionId());
        roleMenu.setUpdateTime(new Date());

		int result = roleMenuService.deleteById(roleMenu);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
