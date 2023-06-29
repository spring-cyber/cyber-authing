package com.cyber.authing.common.util;

import com.cyber.authing.entity.dto.CyberUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author yanwei
 * @desc
 * @since 2023/6/28
 */
public class AuthenticationUtil {

    public static Logger logger = LoggerFactory.getLogger(AuthenticationUtil.class);

    public static String getUserCode() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object != null) {
            return ((CyberUserDetails) object).getUsername();
        }
        return "";
    }
}
