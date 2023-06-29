package com.cyber.authing.entity.request;

import com.cyber.authing.entity.domain.ProjectUser;
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
public class CreateProjectUserRequest extends OperateEntity {

    /**
     * 项目ID
     */
    @NotNull
    private Long projectId;
    /**
     * 用户ID
     */
    @NotEmpty
    private List<Long> userIdList;
    /**
     * 角色
     */
    @NotNull
    private Long roleId;

    public ProjectUser toEvent(String userCode, String tenantCode) {
        ProjectUser projectUser = new ProjectUser();
        BeanUtils.copyProperties(this, projectUser);
        projectUser.setTenantCode(tenantCode);
        projectUser.setCreator(userCode);
        projectUser.setCreateTime(new Date());
        projectUser.setUpdator(userCode);
        projectUser.setUpdateTime(new Date());
        return projectUser;
    }
}