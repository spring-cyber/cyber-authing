package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.RoleMenu;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RoleMenuRequest extends PagingRequest {
	
	
	
	public RoleMenu toEvent(String tenantCode) {
		RoleMenu roleMenu = new RoleMenu();
		BeanUtils.copyProperties(this, roleMenu);
        roleMenu.setTenantCode(tenantCode);
		return roleMenu;
	}
}