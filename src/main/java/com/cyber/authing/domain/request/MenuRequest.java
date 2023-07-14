package com.cyber.authing.domain.request;

import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.Menu;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MenuRequest extends PagingRequest {
	
	
	
	public Menu toEvent(String tenantCode) {
		Menu menu = new Menu();
		BeanUtils.copyProperties(this, menu);
        menu.setTenantCode(tenantCode);
		return menu;
	}
}