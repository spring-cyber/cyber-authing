package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.UserRole;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CreateUserRoleRequest extends OperateEntity {

	

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