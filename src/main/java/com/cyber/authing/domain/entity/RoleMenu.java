package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends PagingEntity {


	/**角色ID*/
	private String roleId;
	/**菜单ID*/
	private String menuId;
}
