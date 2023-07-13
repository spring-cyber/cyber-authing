package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.Menu;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateMenuRequest extends OperateEntity {

		/**产品ID*/	private Long productId;	/**父菜单ID*/	private Long parentId;	/**菜单名称*/	private String name;	/**菜单编码*/	private String code;	/**显示顺序*/	private Integer orderNum;	/**API权限*/	private String apiPerms;

	public Menu toEvent(String userCode,String tenantCode) {
		Menu menu = new Menu();
		BeanUtils.copyProperties(this, menu);

        menu.setTenantCode(tenantCode);
        menu.setUpdator(userCode);
        menu.setUpdateTime(new Date());
		return menu;
	}
}
