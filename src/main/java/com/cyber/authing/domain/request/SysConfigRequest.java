package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.SysConfig;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SysConfigRequest extends PagingRequest {
	
	
	
	public SysConfig toEvent(String tenantCode) {
		SysConfig sysConfig = new SysConfig();
		BeanUtils.copyProperties(this, sysConfig);
        sysConfig.setTenantCode(tenantCode);
		return sysConfig;
	}
}