package com.cyber.authing.entity.request;

import com.cyber.authing.entity.domain.UserRole;
import com.cyber.domain.entity.PagingRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleRequest extends PagingRequest {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;

    public UserRole toEvent(String tenantCode) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(this, userRole);
        userRole.setTenantCode(tenantCode);
        return userRole;
    }
}