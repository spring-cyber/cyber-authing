package com.cyber.authing.domain.entity;import com.cyber.domain.entity.PagingEntity;import lombok.Data;import lombok.EqualsAndHashCode;import java.util.List;@Data@EqualsAndHashCode(callSuper = true)public class Role extends PagingEntity {	/**产品ID*/	private String productId;	/**角色名称*/	private String name;	/**角色编码*/	private String code;	/**用户类型（0系统角色 1自定义角色）*/	private Integer type;	/**角色描述*/	private String description;	/**显示顺序*/	private Integer orderNum;	/**菜单ids*/	private List<String> menuIds;}