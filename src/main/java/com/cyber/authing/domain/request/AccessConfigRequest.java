package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.AccessConfig;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AccessConfigRequest extends PagingRequest {
	
	
	
	public AccessConfig toEvent(String tenantCode) {
		AccessConfig accessConfig = new AccessConfig();
		BeanUtils.copyProperties(this, accessConfig);
        accessConfig.setTenantCode(tenantCode);
		return accessConfig;
	}
}