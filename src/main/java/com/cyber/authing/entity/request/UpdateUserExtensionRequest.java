package com.cyber.authing.entity.request;

import com.alibaba.fastjson.JSONArray;
import com.cyber.authing.entity.domain.UserExtension;
import com.cyber.domain.entity.IdRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateUserExtensionRequest extends IdRequest {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 部门岗位信息
     */
    private JSONArray deptInfo;

    public UserExtension toEvent(String userCode, String tenantCode) {
        UserExtension userExtension = new UserExtension();
        BeanUtils.copyProperties(this, userExtension);
        userExtension.setTenantCode(tenantCode);
        userExtension.setUpdator(userCode);
        userExtension.setUpdateTime(new Date());
        return userExtension;
    }
}