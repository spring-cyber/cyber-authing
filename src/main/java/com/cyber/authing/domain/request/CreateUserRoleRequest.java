package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.UserRole;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CreateUserRoleRequest extends OperateEntity {


	/**产品ID*/
	private String productId;
	/**用户ID*/
	private String userId;
	/**角色ID*/
	private String roleId;
	/**授权描述*/
	private String description;

	public UserRole toEvent(String userCode,String tenantCode) {
		UserRole userRole = new UserRole();
		BeanUtils.copyProperties(this, userRole);

        userRole.setTenantCode(tenantCode);
        userRole.setCreator(userCode);
		userRole.setCreateTime(new Date());

        userRole.setUpdator(userCode);
		userRole.setUpdateTime(new Date());

		return userRole;
	}
}
