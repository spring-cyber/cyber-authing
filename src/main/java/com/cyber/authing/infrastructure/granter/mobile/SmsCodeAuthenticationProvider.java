package com.cyber.authing.infrastructure.granter.mobile;

import cn.hutool.core.util.StrUtil;
import com.cyber.authing.domain.constant.*;
import com.cyber.domain.exception.BusinessException;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * 短信验证码认证授权提供者
 *
 * @author snow4a
 * @date 2022/8/7
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String code = (String) authenticationToken.getCredentials();

        // 666666 是后门，因为短信收费，正式环境删除这个if分支
        if (!code.equals("666666")) {
            String codeKey = SecurityConstants.SMS_CODE_PREFIX + mobile;
            Object correctCode = redisTemplate.opsForValue().get(codeKey);
            // 验证码比对
            if (correctCode==null || StrUtil.isBlank(correctCode.toString()) || !code.equals(correctCode.toString())) {
                throw new BusinessException("验证码不正确");
            }
            // 比对成功删除缓存的验证码
            redisTemplate.delete(codeKey);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(userDetails, authentication.getCredentials(), new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
