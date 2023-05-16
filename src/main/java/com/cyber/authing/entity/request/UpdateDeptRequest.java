package com.cyber.authing.entity.request;import com.cyber.authing.entity.domain.Dept;import com.cyber.domain.entity.IdRequest;import lombok.Data;import lombok.EqualsAndHashCode;import org.springframework.beans.BeanUtils;import java.util.Date;@EqualsAndHashCode(callSuper = true)@Datapublic class UpdateDeptRequest extends IdRequest {    /**     * 父部门id     */    private Long parentId;    /**     * 部门编码     */    private String code;    /**     * 部门名称     */    private String name;    /**     * 显示顺序     */    private Integer orderNum;    /**     * 负责人ID     */    private Long leaderId;    /**     * 联系电话     */    private String phone;    /**     * 邮箱     */    private String email;    public Dept toEvent(String userCode, String tenantCode) {        Dept dept = new Dept();        BeanUtils.copyProperties(this, dept);        dept.setTenantCode(tenantCode);        dept.setUpdator(userCode);        dept.setUpdateTime(new Date());        return dept;    }}