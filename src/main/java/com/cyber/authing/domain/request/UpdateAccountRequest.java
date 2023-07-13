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
public class UpdateAccountRequest extends OperateEntity {

		/**用户ID*/	private Long userId;	/**登录账号*/	private String account;	/**密码，系统登录必选，第三方登录可空*/	private String password;	/**账号类型 0 系统账号 1 微信 */	private Integer type;

	public Account toEvent(String userCode,String tenantCode) {
		Account account = new Account();
		BeanUtils.copyProperties(this, account);

        account.setTenantCode(tenantCode);
        account.setUpdator(userCode);
        account.setUpdateTime(new Date());
		return account;
	}
}
