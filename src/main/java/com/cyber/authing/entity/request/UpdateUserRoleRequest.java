package com.cyber.authing.entity.request;

import com.cyber.authing.entity.domain.UserRole;
import com.cyber.domain.entity.IdRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateUserRoleRequest extends IdRequest {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;

    public UserRole toEvent(String userCode, String tenantCode) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(this, userRole);
        userRole.setTenantCode(tenantCode);
        userRole.setUpdator(userCode);
        userRole.setUpdateTime(new Date());
        return userRole;
    }
}