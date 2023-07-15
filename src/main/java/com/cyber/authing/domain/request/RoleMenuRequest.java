package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.RoleMenu;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RoleMenuRequest extends PagingRequest {


	/**角色ID*/
	private String roleId;
	/**菜单ID*/
	private String menuId;

	public RoleMenu toEvent(String tenantCode) {
		RoleMenu roleMenu = new RoleMenu();
		BeanUtils.copyProperties(this, roleMenu);
        roleMenu.setTenantCode(tenantCode);
		return roleMenu;
	}
}
