package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPosition extends PagingEntity {


	/**用户ID*/
	private Long userId;
	/**岗位ID*/
	private Long positionId;
	/**岗位名称*/
	private String name;
}
