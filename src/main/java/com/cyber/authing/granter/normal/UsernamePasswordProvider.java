package com.cyber.authing.granter.normal;

import com.cyber.authing.entity.dto.CyberUserDetails;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/8
 */
public class UsernamePasswordProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {
    private final NormalUserDetailsService normalUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public UsernamePasswordProvider(NormalUserDetailsService normalUserDetailsService, PasswordEncoder passwordEncoder) {
        this.normalUserDetailsService = normalUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authentication;
            String username = usernamePasswordToken.getPrincipal().toString();
            String password = usernamePasswordToken.getCredentials().toString();
            CyberUserDetails normalUserDetails = (CyberUserDetails) normalUserDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, normalUserDetails.getPassword())) {
                throw new RuntimeException("账号或者密码错误");
            }
            // 将权限列表加入MyUsernamePasswordToken
            UsernamePasswordToken myUsernameAuthenticationToken = UsernamePasswordToken.authenticated(normalUserDetails, null, null);
            myUsernameAuthenticationToken.setDetails(authentication.getDetails());
            return myUsernameAuthenticationToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordToken.class.isAssignableFrom(authentication);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(normalUserDetailsService, "userDetailsService must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
