package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.UserPosition;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserPositionRequest extends PagingRequest {


	/**用户ID*/
	private String userId;
	/**岗位ID*/
	private String positionId;

	public UserPosition toEvent(String tenantCode) {
		UserPosition userPosition = new UserPosition();
		BeanUtils.copyProperties(this, userPosition);
        userPosition.setTenantCode(tenantCode);
		return userPosition;
	}
}
