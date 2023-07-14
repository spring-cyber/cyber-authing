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
public class UpdateUserRoleRequest extends OperateEntity {

	

	public UserRole toEvent(String userCode,String tenantCode) {
		UserRole userRole = new UserRole();
		BeanUtils.copyProperties(this, userRole);

        userRole.setTenantCode(tenantCode);
        userRole.setUpdator(userCode);
        userRole.setUpdateTime(new Date());
		return userRole;
	}
}