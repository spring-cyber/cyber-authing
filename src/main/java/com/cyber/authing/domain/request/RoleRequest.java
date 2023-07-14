package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.Role;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RoleRequest extends PagingRequest {
	
	
	
	public Role toEvent(String tenantCode) {
		Role role = new Role();
		BeanUtils.copyProperties(this, role);
        role.setTenantCode(tenantCode);
		return role;
	}
}