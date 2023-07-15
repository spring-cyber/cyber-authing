package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDept extends PagingEntity {


	/**用户ID*/
	private String userId;
	/**部门ID*/
	private String deptId;
	/**部门名称**/
	private String name;
}
