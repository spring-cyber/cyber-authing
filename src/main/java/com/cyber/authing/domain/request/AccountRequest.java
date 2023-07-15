package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.Account;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AccountRequest extends PagingRequest {


	/**用户ID*/
	private String userId;
	/**登录账号*/
	private String account;
	/**密码，系统登录必选，第三方登录可空*/
	private String password;
	/**账号类型 0 系统账号 1 微信 */
	private Integer type;

	public Account toEvent(String tenantCode) {
		Account account = new Account();
		BeanUtils.copyProperties(this, account);
        account.setTenantCode(tenantCode);
		return account;
	}
}
