package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.AccessConfig;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CreateAccessConfigRequest extends OperateEntity {

		/**接入系统*/	private String accessSys;	/**接入Key*/	private String accessKey;	/**私钥:加密存储*/	private String privateKey;	/**公钥:加密存储*/	private String publicKey;	/**会话策略配置*/	private String policyConfig;

	public AccessConfig toEvent(String userCode,String tenantCode) {
		AccessConfig accessConfig = new AccessConfig();
		BeanUtils.copyProperties(this, accessConfig);

        accessConfig.setTenantCode(tenantCode);
        accessConfig.setCreator(userCode);
		accessConfig.setCreateTime(new Date());

        accessConfig.setUpdator(userCode);
		accessConfig.setUpdateTime(new Date());

		return accessConfig;
	}
}
