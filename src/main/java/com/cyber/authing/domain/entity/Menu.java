package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Menu extends PagingEntity {

		/**产品ID*/	private String productId;	/**父菜单ID*/	private String parentId;	/**菜单名称*/	private String name;	/**菜单编码*/	private String code;	/**显示顺序*/	private Integer orderNum;	/**路由地址*/	private String path;	/**组件路径*/	private String component;	/**路由参数*/	private String query;	/**是否为外链（0是 1否）*/	private Integer isFrame;	/**是否缓存（0缓存 1不缓存）*/	private Integer isCache;	/**菜单类型（M目录 C菜单 F按钮）*/	private String menuType;	/**菜单状态（0显示 1隐藏）*/	private Integer visible;	/**API权限*/	private String apiPerms;	/**图标*/	private String icon;
}