package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.Role;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CreateRoleRequest extends OperateEntity {


	/**产品ID*/
	private String productId;
	/**角色名称*/
	private String name;
	/**角色编码*/
	private String code;
	/**用户类型（0系统角色 1自定义角色）*/
	private Integer type;
	/**角色描述*/
	private String description;
	/**显示顺序*/
	private Integer orderNum;
	/**菜单ids*/
	private List<String> menuIds;

	public Role toEvent(String userCode,String tenantCode) {
		Role role = new Role();
		BeanUtils.copyProperties(this, role);

        role.setTenantCode(tenantCode);
        role.setCreator(userCode);
		role.setCreateTime(new Date());

        role.setUpdator(userCode);
		role.setUpdateTime(new Date());

		return role;
	}
}
