package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.UserExtension;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserExtensionRequest extends PagingRequest {


	/**用户ID*/
	private String userId;

	public UserExtension toEvent(String tenantCode) {
		UserExtension userExtension = new UserExtension();
		BeanUtils.copyProperties(this, userExtension);
        userExtension.setTenantCode(tenantCode);
		return userExtension;
	}
}
