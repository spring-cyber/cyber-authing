package com.cyber.authing.entity.domain;import com.cyber.domain.entity.PagingEntity;import lombok.*;@EqualsAndHashCode(callSuper = true)@Data@Builder@AllArgsConstructor@NoArgsConstructorpublic class DeptUser extends PagingEntity {    /**     * 用户ID     */    private Long userId;    /**     * 部门ID     */    private Long deptId;    /**     * 岗位ID     */    private Long positionId;}