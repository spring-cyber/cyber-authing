package com.cyber.authing.entity.request;

import com.cyber.authing.entity.domain.UserRole;
import com.cyber.domain.entity.OperateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserRoleRequest extends OperateEntity {
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
        userRole.setCreator(userCode);
        userRole.setCreateTime(new Date());
        userRole.setUpdator(userCode);
        userRole.setUpdateTime(new Date());

        return userRole;
    }
}