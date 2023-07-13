package com.cyber.authing.domain.entity;

import com.cyber.domain.entity.PagingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityLog extends PagingEntity {

		/**模块标题*/	private String title;	/**业务类型*/	private String businessType;	/**方法名称*/	private String method;	/**请求方式*/	private String requestMethod;	/**请求URL*/	private String requestUrl;	/**客户端地址*/	private String clientIp;	/**请求参数*/	private String requestParam;	/**返回结果*/	private String execResult;
}