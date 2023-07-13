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
public class UpdateDeptRequest extends OperateEntity {

		/**父部门ID*/	private Long parentId;	/**企业ID*/	private Long enterpriseId;	/**部门名称*/	private String name;	/**部门编码*/	private String code;	/**负责人*/	private String leader;	/**联系电话*/	private String phone;	/**联系邮箱*/	private String email;	/**显示顺序*/	private Integer orderNum;	/**部门描述*/	private String description;

	public Dept toEvent(String userCode,String tenantCode) {
		Dept dept = new Dept();
		BeanUtils.copyProperties(this, dept);

        dept.setTenantCode(tenantCode);
        dept.setUpdator(userCode);
        dept.setUpdateTime(new Date());
		return dept;
	}
}
