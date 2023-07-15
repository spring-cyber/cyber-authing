package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.SysNotice;
import com.cyber.domain.entity.PagingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SysNoticeRequest extends PagingRequest {


	/**产品ID*/
	private String productId;
	/**公告标题*/
	private String noticeTitle;
	/**发布范围*/
	private Integer noticeScope;
	/**公告内容*/
	private String noticeContent;

	public SysNotice toEvent(String tenantCode) {
		SysNotice sysNotice = new SysNotice();
		BeanUtils.copyProperties(this, sysNotice);
        sysNotice.setTenantCode(tenantCode);
		return sysNotice;
	}
}
