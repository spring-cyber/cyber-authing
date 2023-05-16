package com.cyber.authing.config;

import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.entity.Response;
import com.cyber.infrastructure.toolkit.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/5
 */
@Component
public class InvalidAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.warn("Jwt authentication failed!", authException);
        Responses.response(response, Response.fail(HttpResultCode.BAD_AUTH.getCode(), "Jwt authentication failed. " + authException.getMessage()));
    }
}
