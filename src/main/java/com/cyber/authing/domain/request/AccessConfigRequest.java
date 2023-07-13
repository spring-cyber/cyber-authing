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
	
		/**接入系统*/	private String accessSys;	/**接入Key*/	private String accessKey;	/**私钥:加密存储*/	private String privateKey;	/**公钥:加密存储*/	private String publicKey;	/**会话策略配置*/	private String policyConfig;
	
	public AccessConfig toEvent(String tenantCode) {
		AccessConfig accessConfig = new AccessConfig();
		BeanUtils.copyProperties(this, accessConfig);
        accessConfig.setTenantCode(tenantCode);
		return accessConfig;
	}
}