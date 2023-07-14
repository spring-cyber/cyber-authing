package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.User;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends PagingRequest {


	/**企业ID*/
	private Long enterpriseId;
	/**岗位ID*/
	private Long positionId;
	/**用户名称*/
	private String name;
	/**用户性别（0男 1女 2未知）*/
	private Integer sex;
	/**用户类型（0系统用户）*/
	private Integer type;
	/**用户邮箱*/
	private String email;
	/**手机号码*/
	private String phone;
	/**头像地址*/
	private String avatar;
	/**部门IDs*/
	private List<Long> deptIds;

	public User toEvent(String tenantCode) {
		User user = new User();
		BeanUtils.copyProperties(this, user);
		if (null!=this.name){
			user.setName("%"+this.name+"%");
		}
		if (null!=this.phone){
			user.setPhone("%"+this.phone+"%");
		}
        user.setTenantCode(tenantCode);
		return user;
	}
}
