package com.cyber.authing.entity.request;import com.cyber.authing.entity.domain.User;import com.cyber.domain.entity.IdRequest;import lombok.Data;import lombok.EqualsAndHashCode;import org.springframework.beans.BeanUtils;import java.util.Date;@EqualsAndHashCode(callSuper = true)@Datapublic class UpdateUserRequest extends IdRequest {    /**     * 用户名称     */    private String name;    /**     * 用户昵称     */    private String nickname;    /**     * 用户类型（0系统用户）     */    private Integer type;    /**     * 用户邮箱     */    private String email;    /**     * 手机号码     */    private String phoneNumber;    /**     * 用户性别（0男 1女 2未知）     */    private Integer sex;    /**     * 头像地址     */    private String avatar;    /**     * 租户信息     */    private String tenantCode;    public User toEvent(String userCode) {        User user = new User();        BeanUtils.copyProperties(this, user);        user.setUpdator(userCode);        user.setUpdateTime(new Date());        return user;    }}