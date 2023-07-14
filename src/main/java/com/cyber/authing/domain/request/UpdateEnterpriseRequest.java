package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.Enterprise;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateEnterpriseRequest extends OperateEntity {

	

	public Enterprise toEvent(String userCode,String tenantCode) {
		Enterprise enterprise = new Enterprise();
		BeanUtils.copyProperties(this, enterprise);

        enterprise.setTenantCode(tenantCode);
        enterprise.setUpdator(userCode);
        enterprise.setUpdateTime(new Date());
		return enterprise;
	}
}