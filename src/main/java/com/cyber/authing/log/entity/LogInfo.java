package com.cyber.authing.log.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogInfo implements Serializable {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 项目编码
     */
    private String projectCode;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 租户编码
     */
    private String tenantCode;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 请求URL
     */
    private String requestUrl;
    /**
     * 操作描述信息
     */
    private String content;
    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 请求时间
     */
    private String requestTime;
    /**
     * 执行结果
     */
    private String execResult;
    /**
     * 执行时间
     */
    private Long execTime;
}
