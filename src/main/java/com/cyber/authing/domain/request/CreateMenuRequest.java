package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.Menu;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CreateMenuRequest extends OperateEntity {


	/**产品ID*/
	private String productId;
	/**父菜单ID*/
	private String parentId;
	/**菜单名称*/
	private String name;
	/**菜单编码*/
	private String code;
	/**显示顺序*/
	private Integer orderNum;
	/**路由地址*/
	private String path;
	/**组件路径*/
	private String component;
	/**路由参数*/
	private String query;
	/**是否为外链（0是 1否）*/
	private Integer isFrame;
	/**是否缓存（0缓存 1不缓存）*/
	private Integer isCache;
	/**菜单类型（M目录 C菜单 F按钮）*/
	private String menuType;
	/**菜单状态（0显示 1隐藏）*/
	private Integer visible;
	/**API权限*/
	private String apiPerms;
	/**图标*/
	private String icon;

	public Menu toEvent(String userCode,String tenantCode) {
		Menu menu = new Menu();
		BeanUtils.copyProperties(this, menu);

        menu.setTenantCode(tenantCode);
        menu.setCreator(userCode);
		menu.setCreateTime(new Date());

        menu.setUpdator(userCode);
		menu.setUpdateTime(new Date());

		return menu;
	}
}
