package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.ActivityLog;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateActivityLogRequest extends OperateEntity {

	

	public ActivityLog toEvent(String userCode,String tenantCode) {
		ActivityLog activityLog = new ActivityLog();
		BeanUtils.copyProperties(this, activityLog);

        activityLog.setTenantCode(tenantCode);
        activityLog.setUpdator(userCode);
        activityLog.setUpdateTime(new Date());
		return activityLog;
	}
}