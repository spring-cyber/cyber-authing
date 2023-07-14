package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.ActivityLog;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ActivityLogRequest extends PagingRequest {
	
	
	
	public ActivityLog toEvent(String tenantCode) {
		ActivityLog activityLog = new ActivityLog();
		BeanUtils.copyProperties(this, activityLog);
        activityLog.setTenantCode(tenantCode);
		return activityLog;
	}
}