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
public class CreateRoleRequest extends OperateEntity {

	

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