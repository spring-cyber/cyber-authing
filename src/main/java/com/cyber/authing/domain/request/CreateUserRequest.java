package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.User;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CreateUserRequest extends OperateEntity {


	/**企业ID*/
	private String enterpriseId;
	/**部门IDs*/
	private List<String> deptIds;
	/**岗位IDs*/
	private List<String> positionIds;
	/**用户名称*/
	private String name;
	/**用户性别（0男 1女 2未知）*/
	private Integer sex;
	/**用户类型（0系统用户）*/
	private Integer type;
	/**用户邮箱*/
	private String email;
	/**手机号码*/
	private String phone;
	/**头像地址*/
	private String avatar;
	/**登录账号*/
	private String account;
	/**密码，系统登录必选，第三方登录可空*/
	private String password;

	public User toEvent(String userCode,String tenantCode) {
		User user = new User();
		BeanUtils.copyProperties(this, user);

        user.setTenantCode(tenantCode);
        user.setCreator(userCode);
		user.setCreateTime(new Date());

        user.setUpdator(userCode);
		user.setUpdateTime(new Date());

		return user;
	}
}
