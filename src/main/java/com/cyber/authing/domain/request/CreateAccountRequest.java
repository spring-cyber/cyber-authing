package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.Account;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CreateAccountRequest extends OperateEntity {

	

	public Account toEvent(String userCode,String tenantCode) {
		Account account = new Account();
		BeanUtils.copyProperties(this, account);

        account.setTenantCode(tenantCode);
        account.setCreator(userCode);
		account.setCreateTime(new Date());

        account.setUpdator(userCode);
		account.setUpdateTime(new Date());

		return account;
	}
}