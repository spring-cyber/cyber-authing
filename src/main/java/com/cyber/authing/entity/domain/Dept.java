package com.cyber.authing.entity.domain;import com.cyber.domain.entity.PagingEntity;import lombok.Data;import lombok.EqualsAndHashCode;@EqualsAndHashCode(callSuper = true)@Datapublic class Dept extends PagingEntity {    /**     * 父部门id     */    private Long parentId;    /**     * 部门编码     */    private String code;    /**     * 部门名称     */    private String name;    /**     * 显示顺序     */    private Integer orderNum;    /**     * 负责人ID     */    private Long leaderId;    /**     * 联系电话     */    private String phone;    /**     * 邮箱     */    private String email;}