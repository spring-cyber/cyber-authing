package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.UserDept;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserDeptRequest extends PagingRequest {
	
		/**用户ID*/	private Long userId;	/**部门ID*/	private Long deptId;
	
	public UserDept toEvent(String tenantCode) {
		UserDept userDept = new UserDept();
		BeanUtils.copyProperties(this, userDept);
        userDept.setTenantCode(tenantCode);
		return userDept;
	}
}