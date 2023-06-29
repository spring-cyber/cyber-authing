package com.cyber.authing.common.filter;

import com.cyber.authing.common.constant.AuthConstant;
import com.cyber.authing.common.util.JwtTokenProvider;
import com.cyber.authing.entity.domain.User;
import com.cyber.authing.exception.InvalidJwtAuthenticationException;
import com.cyber.authing.entity.dto.CyberUserDetails;
import com.cyber.authing.granter.normal.UsernamePasswordToken;
import com.cyber.authing.service.UserRoleService;
import com.cyber.domain.constant.AuthingTokenKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yanwei
 * @desc
 * @since 2023/4/15
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String reqToken = request.getHeader(AuthingTokenKey.X_CLIENT_JWT_TOKEN);
        logger.info("现在{}正在进行jwt认证:{}", request.getRequestURI(), reqToken);
        String publicKey = jwtTokenProvider.getKey("publicKey");
        if ("/login".equals(request.getRequestURI())){
            filterChain.doFilter(request, response);
            return;
        }
        // 判断token和请求用户是否存在
        if (StringUtils.isBlank(reqToken)) {
            throw new InvalidJwtAuthenticationException("会话不存在");
        }
        // 判断token是否过期
        if (!jwtTokenProvider.validateToken(reqToken, publicKey)) {
            // TODO 需要补刷新token逻辑
            throw new InvalidJwtAuthenticationException("非法token, token已过期");
        }
        // TODO 从redis获取用户信息
        User user = jwtTokenProvider.getUserFromToken(reqToken, publicKey);
        if (null == user) {
            throw new InvalidJwtAuthenticationException("用户不存在");
        }
        // 获取用户角色
        List<String> permsByUserId = queryUserPerms(user.getId());
        CyberUserDetails normalUserDetails = new CyberUserDetails(null, user, permsByUserId);
        if (normalUserDetails.getUser().getStatus() == 1) {
            // TODO redis删除用户
            throw new InvalidJwtAuthenticationException("账户被禁用，请重新登录");
        }
        UsernamePasswordToken myUsernamePasswordToken = new UsernamePasswordToken(normalUserDetails, null,
                normalUserDetails.getAuthorities());
        // TODO 将认证的信息放入 SecurityContextHolder.getContext()
        SecurityContextHolder.getContext().setAuthentication(myUsernamePasswordToken);
        filterChain.doFilter(request, response);
    }

    private List<String> queryUserPerms(String userId) {
        String redisKey = AuthConstant.PERMISSION_PREFIX.getKey() + userId;
        // TODO 从Redis获取权限列表 为空则从数据库获取
        return userRoleService.selectPermsByUserId(userId);
    }
}
