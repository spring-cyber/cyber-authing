package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysNotice extends PagingEntity {

		/**产品ID*/	private Long productId;	/**公告标题*/	private String noticeTitle;	/**发布范围*/	private Integer noticeScope;	/**公告内容*/	private String noticeContent;
}