package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends PagingEntity {

		/**产品ID*/	private Long productId;	/**参数名称*/	private String configName;	/**参数编码*/	private String configCode;	/**参数键名*/	private String configKey;	/**参数键值*/	private String configValue;	/**参数类型（0系统参数 1应用参数）*/	private Integer configType;	/**参数描述*/	private String description;
}