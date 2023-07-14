package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.Dept;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CreateDeptRequest extends OperateEntity {

	

	public Dept toEvent(String userCode,String tenantCode) {
		Dept dept = new Dept();
		BeanUtils.copyProperties(this, dept);

        dept.setTenantCode(tenantCode);
        dept.setCreator(userCode);
		dept.setCreateTime(new Date());

        dept.setUpdator(userCode);
		dept.setUpdateTime(new Date());

		return dept;
	}
}