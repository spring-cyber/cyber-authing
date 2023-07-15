package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.UserPosition;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateUserPositionRequest extends OperateEntity {


	/**用户ID*/
	private String userId;
	/**岗位ID*/
	private String positionId;

	public UserPosition toEvent(String userCode,String tenantCode) {
		UserPosition userPosition = new UserPosition();
		BeanUtils.copyProperties(this, userPosition);

        userPosition.setTenantCode(tenantCode);
        userPosition.setUpdator(userCode);
        userPosition.setUpdateTime(new Date());
		return userPosition;
	}
}
