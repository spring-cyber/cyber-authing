package com.cyber.domain.request;

import com.alibaba.fastjson.JSONObject;
import com.cyber.domain.entity.AuthingCallback;
import com.cyber.domain.entity.AuthingRawToken;
import com.cyber.entity.DataResponse;
import com.cyber.infrastructure.config.AuthingConfig;
import com.cyber.infrastructure.config.AuthingSource;

import java.util.List;


public abstract class AuthingDefaultRequest implements AuthingRequest {

    protected AuthingConfig config;
    protected AuthingSource source;

    public AuthingDefaultRequest(AuthingConfig config, AuthingSource source) {
        this.config = config;
        this.source = source;
    }

    protected abstract AuthingRawToken getAccessToken(AuthingCallback authCallback);


    protected abstract AuthingToken<JSONObject> getUserInfo(AuthingRawToken authToken);


    @Override
    public DataResponse login(AuthingCallback authCallback) {
        try {
            AuthingRawToken authToken = this.getAccessToken(authCallback);
            AuthingToken user = this.getUserInfo(authToken);
            return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).data(user).build();
        } catch (Exception e) {
            Log.error("Failed to login with oauth authorization.", e);
            return this.responseError(e);
        }
    }

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

    @Deprecated
    @Override
    public String authorize() {
        return this.authorize(null);
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

    protected String userInfoUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.userInfo()).queryParam("access_token", authToken.getAccessToken()).build();
    }

    protected String revokeUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.revoke()).queryParam("access_token", authToken.getAccessToken()).build();
    }

    protected String getRealState(String state) {
        if (StringUtils.isEmpty(state)) {
            state = UuidUtils.getUUID();
        }
        // 缓存state
        authStateCache.cache(state, state);
        return state;
    }

    protected String doPostAuthorizationCode(String code) {
        return new HttpUtils.post(accessTokenUrl(code)).getBody();
    }

    protected String doGetAuthorizationCode(String code) {
        return new HttpUtils.get(accessTokenUrl(code)).getBody();
    }

    @Deprecated
    protected String doPostUserInfo(AuthingRawToken authToken) {
        return new HttpUtils.post(userInfoUrl(authToken)).getBody();
    }

    protected String doGetUserInfo(AuthingRawToken authToken) {
        return new HttpUtils.get(userInfoUrl(authToken)).getBody();
    }

    @Deprecated
    protected String doPostRevoke(AuthingRawToken authToken) {
        return new HttpUtils.post(revokeUrl(authToken)).getBody();
    }

    protected String doGetRevoke(AuthingRawToken authToken) {
        return new HttpUtils.get(revokeUrl(authToken)).getBody();
    }


    protected String getScopes(String separator, boolean encode, List<String> defaultScopes) {
        List<String> scopes = config.getScopes();
        if (null == scopes || scopes.isEmpty()) {
            if (null == defaultScopes || defaultScopes.isEmpty()) {
                return "";
            }
            scopes = defaultScopes;
        }
        if (null == separator) {
            // 默认为空格
            separator = " ";
        }
        String scopeStr = String.join(separator, scopes);
        return encode ? URLE.urlEncode(scopeStr) : scopeStr;
    }

}
