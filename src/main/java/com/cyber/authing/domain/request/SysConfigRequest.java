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
	
		/**产品ID*/	private Long productId;	/**参数名称*/	private String configName;	/**参数编码*/	private String configCode;	/**参数键名*/	private String configKey;	/**参数键值*/	private String configValue;	/**参数类型（0系统参数 1应用参数）*/	private Integer configType;	/**参数描述*/	private String description;
	
	public SysConfig toEvent(String tenantCode) {
		SysConfig sysConfig = new SysConfig();
		BeanUtils.copyProperties(this, sysConfig);
        sysConfig.setTenantCode(tenantCode);
		return sysConfig;
	}
}