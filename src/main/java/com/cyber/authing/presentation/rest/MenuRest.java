package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateMenuRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.Menu;
import com.cyber.authing.domain.request.MenuRequest;
import com.cyber.authing.domain.request.CreateMenuRequest;

import com.cyber.authing.application.MenuService;

@RestController
@RequiredArgsConstructor
public class MenuRest extends AuthingTokenController{

	private final MenuService menuService;

	@GetMapping("/menu/search")
	public Response searchMenu(@Valid MenuRequest request) {
		DataResponse<PagingData<Menu>> response = new DataResponse<>();
        Menu  menu = request.toEvent(request.getTenantCode());
		PagingData<Menu> menuPage = menuService.selectPage(menu);
		response.setData(menuPage);
		return response;
	}


	@GetMapping("/menu")
	public Response selectOneMenu(@Valid IdRequest idRequest) {
		DataResponse<Menu> response = new DataResponse<>();

		Menu menu = new Menu();
		menu.setId(idRequest.getId());
        menu.setTenantCode(idRequest.getTenantCode());
		menu = menuService.selectOne(menu);

		response.setData(menu);
		return response;
	}

	@PostMapping("/menu")
	public Response saveMenu(@RequestBody @Valid CreateMenuRequest request) {
	    Menu  menu = request.toEvent(getSessionId(),request.getTenantCode());

		int result = menuService.save(menu);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/menu")
	public Response updateMenu(@RequestBody @Valid UpdateMenuRequest request) {
	    Menu  menu = request.toEvent(getSessionId(),request.getTenantCode());
		int result = menuService.updateById(menu);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/menu")
	public Response deleteMenu(@Valid IdRequest idRequest) {
		Menu menu = new Menu();
		menu.setId(idRequest.getId());

		menu.setTenantCode(idRequest.getTenantCode());
		menu.setUpdator(getSessionId());
        menu.setUpdateTime(new Date());

		int result = menuService.deleteById(menu);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
