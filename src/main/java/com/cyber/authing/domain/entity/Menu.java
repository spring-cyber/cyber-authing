package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Menu extends PagingEntity {

		/**产品ID*/	private Long productId;	/**父菜单ID*/	private Long parentId;	/**菜单名称*/	private String name;	/**菜单编码*/	private String code;	/**显示顺序*/	private Integer orderNum;	/**API权限*/	private String apiPerms;
}