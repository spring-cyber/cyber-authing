package com.cyber.authing.granter.normal;

import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.entity.Response;
import com.cyber.infrastructure.toolkit.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/8
 */
@Component
public class UsernamePasswordErrorHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info(exception.getMessage());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        Responses.response(response, Response.fail(HttpResultCode.SERVER_ERROR.getCode(), exception.getMessage()));
    }
}
