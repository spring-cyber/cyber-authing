package com.cyber.authing.presentation.rest;

import java.util.Date;
import javax.validation.Valid;


import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateAccountRequest;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.Account;
import com.cyber.authing.domain.request.AccountRequest;
import com.cyber.authing.domain.request.CreateAccountRequest;

import com.cyber.authing.application.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountRest extends AuthingTokenController{

	private final AccountService accountService;

	@GetMapping("/account/search")
	public Response searchAccount(@Valid AccountRequest request) {
		DataResponse<PagingData<Account>> response = new DataResponse<>();
        Account  account = request.toEvent(request.getTenantCode());
		PagingData<Account> accountPage = accountService.selectPage(account);
		response.setData(accountPage);
		return response;
	}


	@GetMapping("/account")
	public Response selectOneAccount(@Valid IdRequest idRequest) {
		DataResponse<Account> response = new DataResponse<>();

		Account account = new Account();
		account.setId(idRequest.getId());
        account.setTenantCode(idRequest.getTenantCode());
		account = accountService.selectOne(account);

		response.setData(account);
		return response;
	}

	@PostMapping("/account")
	public Response saveAccount(@RequestBody @Valid CreateAccountRequest request) {
	    Account  account = request.toEvent(getSessionId(),request.getTenantCode());

		int result = accountService.save(account);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/account")
	public Response updateAccount(@RequestBody @Valid UpdateAccountRequest request) {
	    Account  account = request.toEvent(getSessionId(),request.getTenantCode());
		int result = accountService.updateById(account);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/account")
	public Response deleteAccount(@Valid IdRequest idRequest) {
		Account account = new Account();
		account.setId(idRequest.getId());

		account.setTenantCode(idRequest.getTenantCode());
		account.setUpdator(getSessionId());
        account.setUpdateTime(new Date());

		int result = accountService.deleteById(account);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
