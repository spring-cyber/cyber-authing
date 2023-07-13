package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.Role;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateRoleRequest extends OperateEntity {

		/**产品ID*/	private Long productId;	/**角色名称*/	private String name;	/**角色编码*/	private String code;	/**用户类型（0系统角色 1自定义角色）*/	private Integer type;	/**角色描述*/	private String description;	/**显示顺序*/	private Integer orderNum;

	public Role toEvent(String userCode,String tenantCode) {
		Role role = new Role();
		BeanUtils.copyProperties(this, role);

        role.setTenantCode(tenantCode);
        role.setUpdator(userCode);
        role.setUpdateTime(new Date());
		return role;
	}
}
