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
	
		/**模块标题*/	private String title;	/**业务类型*/	private String businessType;	/**方法名称*/	private String method;	/**请求方式*/	private String requestMethod;	/**请求URL*/	private String requestUrl;	/**客户端地址*/	private String clientIp;	/**请求参数*/	private String requestParam;	/**返回结果*/	private String execResult;
	
	public ActivityLog toEvent(String tenantCode) {
		ActivityLog activityLog = new ActivityLog();
		BeanUtils.copyProperties(this, activityLog);
        activityLog.setTenantCode(tenantCode);
		return activityLog;
	}
}