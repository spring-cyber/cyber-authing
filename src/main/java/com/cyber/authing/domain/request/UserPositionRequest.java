package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.UserPosition;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserPositionRequest extends PagingRequest {
	
	
	
	public UserPosition toEvent(String tenantCode) {
		UserPosition userPosition = new UserPosition();
		BeanUtils.copyProperties(this, userPosition);
        userPosition.setTenantCode(tenantCode);
		return userPosition;
	}
}