package com.cyber.authing.domain.request;

import com.cyber.authing.domain.entity.AuthingCallback;
import com.cyber.authing.domain.entity.AuthingRawToken;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.exception.BusinessException;

public interface AuthingRequest {


    default String authorize(String state) {
        throw new BusinessException("NOT_IMPLEMENTED");
    }

    default DataResponse login(AuthingCallback authCallback) {
        throw new BusinessException("NOT_IMPLEMENTED");
    }

    default DataResponse revoke(AuthingRawToken authToken) {
        throw new BusinessException("NOT_IMPLEMENTED");
    }

    default DataResponse refresh(AuthingRawToken authToken) {
        throw new BusinessException("NOT_IMPLEMENTED");
    }
}
