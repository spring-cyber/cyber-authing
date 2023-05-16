package com.cyber.authing.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/5
 */
public class InvalidJwtAuthenticationException extends AuthenticationException {
    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }
}
