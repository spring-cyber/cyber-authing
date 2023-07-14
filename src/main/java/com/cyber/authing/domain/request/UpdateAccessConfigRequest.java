package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.AccessConfig;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateAccessConfigRequest extends OperateEntity {

	

	public AccessConfig toEvent(String userCode,String tenantCode) {
		AccessConfig accessConfig = new AccessConfig();
		BeanUtils.copyProperties(this, accessConfig);

        accessConfig.setTenantCode(tenantCode);
        accessConfig.setUpdator(userCode);
        accessConfig.setUpdateTime(new Date());
		return accessConfig;
	}
}