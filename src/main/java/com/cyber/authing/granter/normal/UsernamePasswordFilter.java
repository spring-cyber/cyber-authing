package com.cyber.authing.granter.normal;

import com.alibaba.fastjson.JSON;
import com.cyber.authing.entity.request.LoginRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/8
 */
public class UsernamePasswordFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PasswordEncoder passwordEncoder;

    // 自定拦截路由
    private static final AntPathRequestMatcher NORMAL_LOGIN_REQUEST = new AntPathRequestMatcher("/login", "POST");


    public UsernamePasswordFilter() {
        super(NORMAL_LOGIN_REQUEST);
    }

    public UsernamePasswordFilter(AuthenticationManager authenticationManager) {
        super(NORMAL_LOGIN_REQUEST, authenticationManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            logger.info("url:" + request.getRequestURI());
            // 获取登录表单
            LoginRequest loginRequest = getLoginRequest(request);
            UsernamePasswordToken usernamePasswordToken = UsernamePasswordToken.unauthenticated(loginRequest.getName(), loginRequest.getPassword());
            this.setDetails(request, usernamePasswordToken);
            this.setContinueChainBeforeSuccessfulAuthentication(false);
            return this.getAuthenticationManager().authenticate(usernamePasswordToken);
        }
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordToken usernamePasswordToken) {
        usernamePasswordToken.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    /**
     * 获取登录用户信息
     *
     * @param request
     * @return
     */
    private LoginRequest getLoginRequest(HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            logger.info("LoginRequest object:{}", JSON.toJSONString(loginRequest));
            return loginRequest;
        } catch (Exception e) {
            throw new NullPointerException("获取不到登录信息");
        }
    }
}
