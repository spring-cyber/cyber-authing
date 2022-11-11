package com.cyber.domain.request;

import com.cyber.domain.entity.AuthingCallback;
import com.cyber.domain.entity.AuthingRawToken;
import com.cyber.entity.DataResponse;
import com.cyber.exception.BusinessException;

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
