package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.RoleMenu;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateRoleMenuRequest extends OperateEntity {

		/**角色ID*/	private Long roleId;	/**菜单ID*/	private Long menuId;

	public RoleMenu toEvent(String userCode,String tenantCode) {
		RoleMenu roleMenu = new RoleMenu();
		BeanUtils.copyProperties(this, roleMenu);

        roleMenu.setTenantCode(tenantCode);
        roleMenu.setUpdator(userCode);
        roleMenu.setUpdateTime(new Date());
		return roleMenu;
	}
}
