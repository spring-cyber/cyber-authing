package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.UserRole;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserRoleRequest extends PagingRequest {
	
	
	
	public UserRole toEvent(String tenantCode) {
		UserRole userRole = new UserRole();
		BeanUtils.copyProperties(this, userRole);
        userRole.setTenantCode(tenantCode);
		return userRole;
	}
}