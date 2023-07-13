package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.UserPosition;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateUserPositionRequest extends OperateEntity {

		/**用户ID*/	private Long userId;	/**岗位ID*/	private Long positionId;

	public UserPosition toEvent(String userCode,String tenantCode) {
		UserPosition userPosition = new UserPosition();
		BeanUtils.copyProperties(this, userPosition);

        userPosition.setTenantCode(tenantCode);
        userPosition.setUpdator(userCode);
        userPosition.setUpdateTime(new Date());
		return userPosition;
	}
}
