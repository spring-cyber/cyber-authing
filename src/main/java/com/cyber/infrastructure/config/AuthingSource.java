package com.cyber.infrastructure.config;


import com.cyber.domain.request.AuthingDefaultRequest;
import com.cyber.exception.BusinessException;

public interface AuthingSource {

    String authorize();

    String accessToken();

    String userInfo();

    default String revoke() {
        throw new BusinessException("UNSUPPORTED");
    }

    default String refresh() {
        throw new BusinessException("UNSUPPORTED");
    }

    default String getName() {
        if (this instanceof Enum) {
            return String.valueOf(this);
        }
        return this.getClass().getSimpleName();
    }

    Class<? extends AuthingDefaultRequest> getTargetClass();
}
