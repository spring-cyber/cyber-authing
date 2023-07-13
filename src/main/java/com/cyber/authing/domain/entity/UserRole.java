package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends PagingEntity {

		/**产品ID*/	private Long productId;	/**用户ID*/	private Long userId;	/**角色ID*/	private String roleId;	/**授权描述*/	private String description;
}