package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.Enterprise;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class EnterpriseRequest extends PagingRequest {


	/**企业名称*/
	private String name;
	/**企业编码*/
	private String code;
	/**负责人*/
	private String leader;
	/**联系电话*/
	private String phone;
	/**联系邮箱*/
	private String email;
	/**显示顺序*/
	private Integer orderNum;
	/**企业描述*/
	private String description;

	public Enterprise toEvent(String tenantCode) {
		Enterprise enterprise = new Enterprise();
		BeanUtils.copyProperties(this, enterprise);
        enterprise.setTenantCode(tenantCode);
		return enterprise;
	}
}
