package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.UserDept;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateUserDeptRequest extends OperateEntity {

		/**用户ID*/	private Long userId;	/**部门ID*/	private Long deptId;

	public UserDept toEvent(String userCode,String tenantCode) {
		UserDept userDept = new UserDept();
		BeanUtils.copyProperties(this, userDept);

        userDept.setTenantCode(tenantCode);
        userDept.setUpdator(userCode);
        userDept.setUpdateTime(new Date());
		return userDept;
	}
}
