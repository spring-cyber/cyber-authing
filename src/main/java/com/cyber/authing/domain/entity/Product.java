package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends PagingEntity {

		/**产品名称*/	private String name;	/**产品编码*/	private String code;	/**图标*/	private String icon;	/**门户地址*/	private String portalAdd;	/**产品类型（0系统）*/	private Integer type;	/**显示顺序*/	private Integer orderNum;	/**负责人*/	private String leader;	/**联系电话*/	private String phone;	/**联系邮箱*/	private String email;	/**产品描述*/	private String description;
}