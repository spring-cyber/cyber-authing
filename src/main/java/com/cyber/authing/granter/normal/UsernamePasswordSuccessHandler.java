package com.cyber.authing.granter.normal;

import com.cyber.authing.common.util.JwtTokenProvider;
import com.cyber.authing.entity.domain.User;
import com.cyber.authing.entity.dto.CyberUserDetails;
import com.cyber.authing.entity.dto.UserDTO;
import com.cyber.authing.service.UserRoleService;
import com.cyber.authing.service.UserService;
import com.cyber.domain.entity.DataResponse;
import com.cyber.infrastructure.toolkit.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/8
 */
@Component
public class UsernamePasswordSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserService userService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
        filterChain.doFilter(request, response);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        CyberUserDetails normalUserDetails = (CyberUserDetails) authentication.getPrincipal();
        User user = normalUserDetails.getUser();
        String userId = user.getId();
        //TODO 每次登录设置登录时间和IP
        String token = jwtTokenProvider.createToken(normalUserDetails, jwtTokenProvider.getKey("privateKey"));
        // TODO redis存放权限列表
        List<String> permsByUserId = userRoleService.selectPermsByUserId(userId);
        UserDTO userDTO = new UserDTO(user);
        userDTO.setToken(token);
        DataResponse<UserDTO> responseData = new DataResponse<>();
        responseData.setData(userDTO);
        Responses.response(response, responseData);
        logger.info("登录成功：" + authentication.getPrincipal().toString());
    }
}
