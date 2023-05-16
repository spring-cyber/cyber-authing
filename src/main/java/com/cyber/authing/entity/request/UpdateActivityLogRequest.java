package com.cyber.authing.entity.request;import com.alibaba.fastjson.JSONObject;import com.cyber.authing.entity.domain.ActivityLog;import com.cyber.domain.entity.IdRequest;import lombok.Data;import lombok.EqualsAndHashCode;import org.springframework.beans.BeanUtils;import java.util.Date;@EqualsAndHashCode(callSuper = true)@Datapublic class UpdateActivityLogRequest extends IdRequest {    /**     * 模块标题     */    private String title;    /**     * 业务类型     */    private String businessType;    /**     * 方法名称     */    private String method;    /**     * 请求方式     */    private String requestMethod;    /**     * 操作人员     */    private String operName;    /**     * 请求URL     */    private String operUrl;    /**     * 客户端地址     */    private String clientIp;    /**     * 请求参数     */    private JSONObject requestParam;    /**     * 返回结果     */    private JSONObject responseResult;    public ActivityLog toEvent(String userCode, String tenantCode) {        ActivityLog activityLog = new ActivityLog();        BeanUtils.copyProperties(this, activityLog);        activityLog.setTenantCode(tenantCode);        activityLog.setUpdator(userCode);        activityLog.setUpdateTime(new Date());        return activityLog;    }}