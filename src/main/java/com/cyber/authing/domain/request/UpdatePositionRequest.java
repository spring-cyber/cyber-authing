package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.Position;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdatePositionRequest extends OperateEntity {


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

	public Position toEvent(String userCode,String tenantCode) {
		Position position = new Position();
		BeanUtils.copyProperties(this, position);

		if (this.enterpriseId==null){
			position.setEnterpriseId("0");
		}
        position.setTenantCode(tenantCode);
        position.setUpdator(userCode);
        position.setUpdateTime(new Date());
		return position;
	}
}
