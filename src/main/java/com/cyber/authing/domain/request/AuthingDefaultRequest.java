package com.cyber.authing.domain.request;

import com.alibaba.fastjson.JSONObject;
import com.cyber.authing.domain.entity.AuthingCallback;
import com.cyber.authing.domain.entity.AuthingRawToken;
import com.cyber.domain.entity.AuthingToken;
import com.cyber.domain.entity.DataResponse;
import com.cyber.authing.domain.entity.AuthingSetting;


public abstract class AuthingDefaultRequest implements AuthingRequest {

    protected AuthingSetting setting;

    public AuthingDefaultRequest(AuthingSetting setting) {
        this.setting = setting;
    }

    protected abstract AuthingRawToken getAccessToken(AuthingCallback authCallback);


    protected abstract AuthingToken<JSONObject> getUserInfo(AuthingRawToken authToken);

    DataResponse responseError(Exception e) {
        int errorCode = AuthResponseStatus.FAILURE.getCode();
        String errorMsg = e.getMessage();
        if (e instanceof AuthException) {
            AuthException authException = ((AuthException) e);
            errorCode = authException.getErrorCode();
            if (StringUtils.isNotEmpty(authException.getErrorMsg())) {
                errorMsg = authException.getErrorMsg();
            }
        }
        return AuthResponse.builder().code(errorCode).msg(errorMsg).build();
    }

    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
            .queryParam("response_type", "code")
            .queryParam("client_id", config.getClientId())
            .queryParam("redirect_uri", config.getRedirectUri())
            .queryParam("state", getRealState(state))
            .build();
    }

    protected String accessTokenUrl(String code) {
        return UrlBuilder.fromBaseUrl(source.accessToken())
            .queryParam("code", code)
            .queryParam("client_id", config.getClientId())
            .queryParam("client_secret", config.getClientSecret())
            .queryParam("grant_type", "authorization_code")
            .queryParam("redirect_uri", config.getRedirectUri())
            .build();
    }

    protected String refreshTokenUrl(String refreshToken) {
        return UrlBuilder.fromBaseUrl(source.refresh())
            .queryParam("client_id", config.getClientId())
            .queryParam("client_secret", config.getClientSecret())
            .queryParam("refresh_token", refreshToken)
            .queryParam("grant_type", "refresh_token")
            .queryParam("redirect_uri", config.getRedirectUri())
            .build();
    }

}
