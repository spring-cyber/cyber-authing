package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.SysNotice;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SysNoticeRequest extends PagingRequest {
	
	
	
	public SysNotice toEvent(String tenantCode) {
		SysNotice sysNotice = new SysNotice();
		BeanUtils.copyProperties(this, sysNotice);
        sysNotice.setTenantCode(tenantCode);
		return sysNotice;
	}
}