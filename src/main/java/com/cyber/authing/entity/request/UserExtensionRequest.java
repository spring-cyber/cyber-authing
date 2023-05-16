package com.cyber.authing.entity.request;

import com.alibaba.fastjson.JSONArray;
import com.cyber.authing.entity.domain.UserExtension;
import com.cyber.domain.entity.PagingRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserExtensionRequest extends PagingRequest {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 部门岗位信息
     */
    private JSONArray deptInfo;

    public UserExtension toEvent(String tenantCode) {
        UserExtension userExtension = new UserExtension();
        BeanUtils.copyProperties(this, userExtension);
        userExtension.setTenantCode(tenantCode);
        return userExtension;
    }
}