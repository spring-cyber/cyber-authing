package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.UserExtension;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateUserExtensionRequest extends OperateEntity {

	

	public UserExtension toEvent(String userCode,String tenantCode) {
		UserExtension userExtension = new UserExtension();
		BeanUtils.copyProperties(this, userExtension);

        userExtension.setTenantCode(tenantCode);
        userExtension.setUpdator(userCode);
        userExtension.setUpdateTime(new Date());
		return userExtension;
	}
}