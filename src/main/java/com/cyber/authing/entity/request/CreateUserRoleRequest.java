package com.cyber.authing.entity.request;

import com.cyber.authing.entity.domain.UserRole;
import com.cyber.domain.entity.OperateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserRoleRequest extends OperateEntity {
    /**
     * 用户ID
     */
    @NotNull
    private Long userId;
    /**
     * 角色ID
     */
    @NotEmpty
    private List<Long> roleIds;

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