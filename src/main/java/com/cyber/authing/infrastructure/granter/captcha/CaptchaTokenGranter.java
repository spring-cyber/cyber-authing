/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cyber.authing.infrastructure.granter.captcha;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.cyber.authing.domain.constant.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 验证码授权模式授权者
 *
 * @author snow4aa
 */
public class CaptchaTokenGranter extends AbstractTokenGranter {
	/**
	  * 根据接口传值 grant_type = captcha 的值匹配到此授权者
     * 匹配逻辑详见下面的两个方法
     *
			 * @see CompositeTokenGranter#grant(String, TokenRequest)
     * @see AbstractTokenGranter#grant(String, TokenRequest)
     */
	private static final String GRANT_TYPE = "captcha";
	private final AuthenticationManager authenticationManager;
	private RedisTemplate<String, Object> redisTemplate;

	public CaptchaTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
							   OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager,
							   RedisTemplate<String, Object> redisTemplate
	) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.authenticationManager = authenticationManager;
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

		Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());

		// 验证码校验逻辑
		String validateCode = parameters.get("code");
		String uuid = parameters.get("uuid");

		Assert.isTrue(StrUtil.isNotBlank(validateCode), "验证码不能为空");
		String validateCodeKey = SecurityConstants.VALIDATION_CODE_KEY_PREFIX + uuid;

		// 从缓存取出正确的验证码和用户输入的验证码比对
		Object correctValidateCode = redisTemplate.opsForValue().get(validateCodeKey);
		Assert.isTrue(correctValidateCode!=null,"验证码已过期");
		Assert.isTrue(StrUtil.isNotBlank(String.valueOf(correctValidateCode)),"验证码已过期");
		Assert.isTrue(validateCode.equals(String.valueOf(correctValidateCode)),"您输入的验证码不正确");

		// 验证码验证通过，删除 Redis 的验证码
		redisTemplate.delete(validateCodeKey);

		String username = parameters.get("username");
		String password = parameters.get("password");

		// 移除后续无用参数
		parameters.remove("password");
		parameters.remove("code");
		parameters.remove("uuid");

		// 和密码模式一样的逻辑
		Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
		((AbstractAuthenticationToken) userAuth).setDetails(parameters);

		try {
			userAuth = this.authenticationManager.authenticate(userAuth);
		} catch (AccountStatusException var8) {
			throw new InvalidGrantException(var8.getMessage());
		} catch (BadCredentialsException var9) {
			throw new InvalidGrantException(var9.getMessage());
		}


		if (userAuth != null && userAuth.isAuthenticated()) {
			OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
			return new OAuth2Authentication(storedOAuth2Request, userAuth);
		} else {
			throw new InvalidGrantException("Could not authenticate user: " + username);
		}
	}
}
