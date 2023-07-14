package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.Account;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AccountRequest extends PagingRequest {
	
	
	
	public Account toEvent(String tenantCode) {
		Account account = new Account();
		BeanUtils.copyProperties(this, account);
        account.setTenantCode(tenantCode);
		return account;
	}
}