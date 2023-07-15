package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.SysNotice;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateSysNoticeRequest extends OperateEntity {


	/**产品ID*/
	private String productId;
	/**公告标题*/
	private String noticeTitle;
	/**发布范围*/
	private Integer noticeScope;
	/**公告内容*/
	private String noticeContent;

	public SysNotice toEvent(String userCode,String tenantCode) {
		SysNotice sysNotice = new SysNotice();
		BeanUtils.copyProperties(this, sysNotice);

        sysNotice.setTenantCode(tenantCode);
        sysNotice.setUpdator(userCode);
        sysNotice.setUpdateTime(new Date());
		return sysNotice;
	}
}
