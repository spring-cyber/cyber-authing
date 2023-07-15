package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.UserExtension;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateUserExtensionRequest extends OperateEntity {


	/**用户ID*/
	private String userId;

	public UserExtension toEvent(String userCode,String tenantCode) {
		UserExtension userExtension = new UserExtension();
		BeanUtils.copyProperties(this, userExtension);

        userExtension.setTenantCode(tenantCode);
        userExtension.setUpdator(userCode);
        userExtension.setUpdateTime(new Date());
		return userExtension;
	}
}
