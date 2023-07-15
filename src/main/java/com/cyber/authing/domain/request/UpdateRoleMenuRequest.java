package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.RoleMenu;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateRoleMenuRequest extends OperateEntity {


	/**角色ID*/
	private String roleId;
	/**菜单ID*/
	private String menuId;

	public RoleMenu toEvent(String userCode,String tenantCode) {
		RoleMenu roleMenu = new RoleMenu();
		BeanUtils.copyProperties(this, roleMenu);

        roleMenu.setTenantCode(tenantCode);
        roleMenu.setUpdator(userCode);
        roleMenu.setUpdateTime(new Date());
		return roleMenu;
	}
}
