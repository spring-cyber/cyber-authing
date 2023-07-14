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

	

	public Menu toEvent(String userCode,String tenantCode) {
		Menu menu = new Menu();
		BeanUtils.copyProperties(this, menu);

        menu.setTenantCode(tenantCode);
        menu.setUpdator(userCode);
        menu.setUpdateTime(new Date());
		return menu;
	}
}