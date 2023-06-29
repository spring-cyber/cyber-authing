package com.cyber.authing.controller;

import com.cyber.authing.common.util.JwtTokenProvider;
import com.cyber.authing.entity.domain.User;
import com.cyber.authing.entity.dto.CyberUserDetails;
import com.cyber.authing.entity.dto.UserDTO;
import com.cyber.authing.entity.request.LoginRequest;
import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/5
 */
@RestController
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/login")
    public Response login(@Valid @RequestBody LoginRequest loginRequest) {
        DataResponse<UserDTO> responseData = new DataResponse<>();
        logger.debug("用户 {} 开始登录。", loginRequest.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String publicKey = jwtTokenProvider.getKey("publicKey");
            CyberUserDetails securityUserDetails = (CyberUserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.createToken(securityUserDetails, publicKey);
            User user = User.builder().name(loginRequest.getUsername()).build();
            UserDTO userInfo = new UserDTO(user);
            userInfo.setToken(token);
            responseData.setData(userInfo);
        } catch (BadCredentialsException e) {
            responseData.setCode(HttpResultCode.SERVER_ERROR.getCode());
            responseData.setMessage("账号或密码错误！");
        }
        return responseData;
    }

    @GetMapping("/currentUser")
    public Response currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        DataResponse<UserDetails> responseData = new DataResponse<>();
        responseData.setData(userDetails);
        return responseData;
    }
}
