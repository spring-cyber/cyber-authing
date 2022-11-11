package com.cyber.infrastructure.config;


import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class AuthingConfig {

    private String clientId;


    private String clientSecret;


    private String redirectUri;

    private boolean unionId;

    private String stackOverflowKey;

    private String domainPrefix;
    private boolean ignoreCheckState;

    private List<String> scopes;

    private String deviceId;

    private boolean pkce;

    private String authServerId;
    private boolean ignoreCheckRedirectUri;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public boolean isUnionId() {
        return unionId;
    }

    public void setUnionId(boolean unionId) {
        this.unionId = unionId;
    }

    public String getStackOverflowKey() {
        return stackOverflowKey;
    }

    public void setStackOverflowKey(String stackOverflowKey) {
        this.stackOverflowKey = stackOverflowKey;
    }

    public String getDomainPrefix() {
        return domainPrefix;
    }

    public void setDomainPrefix(String domainPrefix) {
        this.domainPrefix = domainPrefix;
    }

    public boolean isIgnoreCheckState() {
        return ignoreCheckState;
    }

    public void setIgnoreCheckState(boolean ignoreCheckState) {
        this.ignoreCheckState = ignoreCheckState;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isPkce() {
        return pkce;
    }

    public void setPkce(boolean pkce) {
        this.pkce = pkce;
    }

    public String getAuthServerId() {
        return authServerId;
    }

    public void setAuthServerId(String authServerId) {
        this.authServerId = authServerId;
    }

    public boolean isIgnoreCheckRedirectUri() {
        return ignoreCheckRedirectUri;
    }

    public void setIgnoreCheckRedirectUri(boolean ignoreCheckRedirectUri) {
        this.ignoreCheckRedirectUri = ignoreCheckRedirectUri;
    }
}
