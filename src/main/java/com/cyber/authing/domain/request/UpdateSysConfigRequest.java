package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.SysConfig;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateSysConfigRequest extends OperateEntity {

	

	public SysConfig toEvent(String userCode,String tenantCode) {
		SysConfig sysConfig = new SysConfig();
		BeanUtils.copyProperties(this, sysConfig);

        sysConfig.setTenantCode(tenantCode);
        sysConfig.setUpdator(userCode);
        sysConfig.setUpdateTime(new Date());
		return sysConfig;
	}
}