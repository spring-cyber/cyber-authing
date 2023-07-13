package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.SysNotice;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateSysNoticeRequest extends OperateEntity {

		/**产品ID*/	private Long productId;	/**公告标题*/	private String noticeTitle;	/**发布范围*/	private Integer noticeScope;	/**公告内容*/	private String noticeContent;

	public SysNotice toEvent(String userCode,String tenantCode) {
		SysNotice sysNotice = new SysNotice();
		BeanUtils.copyProperties(this, sysNotice);

        sysNotice.setTenantCode(tenantCode);
        sysNotice.setUpdator(userCode);
        sysNotice.setUpdateTime(new Date());
		return sysNotice;
	}
}
