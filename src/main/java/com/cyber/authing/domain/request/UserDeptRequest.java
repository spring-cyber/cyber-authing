package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.UserDept;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserDeptRequest extends PagingRequest {


	/**用户ID*/
	private String userId;
	/**部门ID*/
	private String deptId;

	public UserDept toEvent(String tenantCode) {
		UserDept userDept = new UserDept();
		BeanUtils.copyProperties(this, userDept);
        userDept.setTenantCode(tenantCode);
		return userDept;
	}
}
