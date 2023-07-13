package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDept extends PagingEntity {

		/**用户ID*/	private Long userId;	/**部门ID*/	private Long deptId;
}