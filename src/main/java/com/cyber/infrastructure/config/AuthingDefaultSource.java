package com.cyber.infrastructure.config;


import com.cyber.domain.request.*;

public enum AuthingDefaultSource implements AuthingSource {
    ALIPAY {
        @Override
        public String authorize() {
            return "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";
        }

        @Override
        public String accessToken() {
            return "https://openapi.alipay.com/gateway.do";
        }

        @Override
        public String userInfo() {
            return "https://openapi.alipay.com/gateway.do";
        }

        @Override
        public Class<? extends AuthingDefaultRequest> getTargetClass() {
            return AuthingAlipayRequest.class;
        }
    },
    WECHAT_OPEN {
        @Override
        public String authorize() {
            return "https://open.weixin.qq.com/connect/qrconnect";
        }

        @Override
        public String accessToken() {
            return "https://api.weixin.qq.com/sns/oauth2/access_token";
        }

        @Override
        public String userInfo() {
            return "https://api.weixin.qq.com/sns/userinfo";
        }

        @Override
        public String refresh() {
            return "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        }

        @Override
        public Class<? extends AuthingDefaultRequest> getTargetClass() {
            return AuthingWeChatOpenRequest.class;
        }
    },
    GOOGLE {
        @Override
        public String authorize() {
            return "https://accounts.google.com/o/oauth2/v2/auth";
        }

        @Override
        public String accessToken() {
            return "https://www.googleapis.com/oauth2/v4/token";
        }

        @Override
        public String userInfo() {
            return "https://www.googleapis.com/oauth2/v3/userinfo";
        }

        @Override
        public Class<? extends AuthingDefaultRequest> getTargetClass() {
            return AuthingGoogleRequest.class;
        }
    },
    FACEBOOK {
        @Override
        public String authorize() {
            return "https://www.facebook.com/v10.0/dialog/oauth";
        }

        @Override
        public String accessToken() {
            return "https://graph.facebook.com/v10.0/oauth/access_token";
        }

        @Override
        public String userInfo() {
            return "https://graph.facebook.com/v10.0/me";
        }

        @Override
        public Class<? extends AuthingDefaultRequest> getTargetClass() {
            return AuthingFacebookRequest.class;
        }
    },
    TWITTER {
        @Override
        public String authorize() {
            return "https://api.twitter.com/oauth/authenticate";
        }

        @Override
        public String accessToken() {
            return "https://api.twitter.com/oauth/access_token";
        }

        @Override
        public String userInfo() {
            return "https://api.twitter.com/1.1/account/verify_credentials.json";
        }

        @Override
        public Class<? extends AuthingDefaultRequest> getTargetClass() {
            return AuthingTwitterRequest.class;
        }
    },
    AMAZON {
        @Override
        public String authorize() {
            return "https://www.amazon.com/ap/oa";
        }

        @Override
        public String accessToken() {
            return "https://api.amazon.com/auth/o2/token";
        }

        @Override
        public String userInfo() {
            return "https://api.amazon.com/user/profile";
        }

        @Override
        public String refresh() {
            return "https://api.amazon.com/auth/o2/token";
        }

        @Override
        public Class<? extends AuthingDefaultRequest> getTargetClass() {
            return AuthingAmazonRequest.class;
        }
    }
}
