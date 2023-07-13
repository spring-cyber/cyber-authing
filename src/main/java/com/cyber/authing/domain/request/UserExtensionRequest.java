package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.UserExtension;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserExtensionRequest extends PagingRequest {
	
		/**用户ID*/	private Long userId;
	
	public UserExtension toEvent(String tenantCode) {
		UserExtension userExtension = new UserExtension();
		BeanUtils.copyProperties(this, userExtension);
        userExtension.setTenantCode(tenantCode);
		return userExtension;
	}
}