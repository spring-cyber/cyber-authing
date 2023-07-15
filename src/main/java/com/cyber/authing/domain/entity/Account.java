package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends PagingEntity {


	/**用户ID*/
	private String userId;
	/**登录账号*/
	private String account;
	/**密码，系统登录必选，第三方登录可空*/
	private String password;
	/**账号类型 0 系统账号 1 微信 */
	private Integer type;
}
