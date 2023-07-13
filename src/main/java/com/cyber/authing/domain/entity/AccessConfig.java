package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccessConfig extends PagingEntity {

		/**接入系统*/	private String accessSys;	/**接入Key*/	private String accessKey;	/**私钥:加密存储*/	private String privateKey;	/**公钥:加密存储*/	private String publicKey;	/**会话策略配置*/	private String policyConfig;
}