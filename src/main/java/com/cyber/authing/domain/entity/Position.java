package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Position extends PagingEntity {


	/**企业ID*/
	private Long enterpriseId;
	/**企业名称*/
	private String enterpriseName;
	/**岗位名称*/
	private String name;
	/**岗位编码*/
	private String code;
	/**显示顺序*/
	private Integer orderNum;
	/**岗位描述*/
	private String description;
}
