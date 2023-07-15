package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.Position;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class PositionRequest extends PagingRequest {


	/**企业ID*/
	private String enterpriseId;
	/**岗位名称*/
	private String name;
	/**岗位编码*/
	private String code;
	/**显示顺序*/
	private Integer orderNum;
	/**岗位描述*/
	private String description;

	public Position toEvent(String tenantCode) {
		Position position = new Position();
		BeanUtils.copyProperties(this, position);
        position.setTenantCode(tenantCode);
		return position;
	}
}
