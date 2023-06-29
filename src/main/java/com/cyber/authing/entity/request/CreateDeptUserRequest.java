package com.cyber.authing.entity.request;

import com.cyber.authing.entity.domain.DeptUser;
import com.cyber.domain.entity.OperateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateDeptUserRequest extends OperateEntity {

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;
    /**
     * 部门ID
     */
    @NotNull
    private Long deptId;
    /**
     * 岗位ID
     */
    private Long positionId;

    public DeptUser toEvent(String userCode, String tenantCode) {
        DeptUser deptUser = new DeptUser();
        BeanUtils.copyProperties(this, deptUser);
        deptUser.setTenantCode(tenantCode);
        deptUser.setCreator(userCode);
        deptUser.setCreateTime(new Date());
        deptUser.setUpdator(userCode);
        deptUser.setUpdateTime(new Date());
        return deptUser;
    }
}