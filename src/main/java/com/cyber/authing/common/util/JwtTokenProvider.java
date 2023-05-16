package com.cyber.authing.common.util;

import com.alibaba.fastjson.JSON;
import com.cyber.authing.entity.domain.User;
import com.cyber.authing.entity.dto.CyberUserDetails;
import com.cyber.authing.exception.InvalidJwtAuthenticationException;
import com.google.common.base.Throwables;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanwei
 * @desc
 * @since 2023/4/16
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    /**
     * 用户信息
     */
    private static final String USER_OBJECT = "user";

    /**
     * 根据账号、角色信息创建token
     *
     * @param cyberUserDetails
     * @return
     */
    public String createToken(CyberUserDetails cyberUserDetails, String privateKey) {
        return this.createToken(cyberUserDetails, privateKey, EXPIRATION_TIME);
    }

    /**
     * 根据账号、角色信息创建token
     *
     * @param cyberUserDetails
     * @return
     */
    public String createToken(CyberUserDetails cyberUserDetails, String privateKey, long expirationTime) {
        Claims claims = Jwts.claims().setSubject(cyberUserDetails.getUsername());
        claims.put("roles", cyberUserDetails.getAuthorities());
        claims.put(USER_OBJECT, cyberUserDetails.getUser());
        return generateToken(claims, privateKey, expirationTime);
    }

    public String refreshToken(String token, String publicKey, String privateKey, long expirationTime) {
        Claims claims = Jwts.parser().setSigningKey(getRSAPublicKey(publicKey)).parseClaimsJws(token).getBody();
        return generateToken(claims, privateKey, expirationTime);
    }

    private String generateToken(Claims claims, String privateKey, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getRSAPrivateKey(privateKey), SignatureAlgorithm.RS256).compact();
    }

    public String getUsernameFromToken(String token, String publicKey) {
        return Jwts.parser().setSigningKey(getRSAPublicKey(publicKey)).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, String publicKey) {
        try {
            Claims claims = Jwts.parser().setSigningKey(getRSAPublicKey(publicKey)).parseClaimsJws(token).getBody();
            return new Date().before(claims.getExpiration());
        } catch (ExpiredJwtException e) {
            logger.warn("Expired JWT token. {}", token);
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Invalid JWT token");
        }
    }

    public Date getTokenExpiration(String token, String publicKey) {
        return Jwts.parser().setSigningKey(getRSAPublicKey(publicKey)).parseClaimsJws(token).getBody().getExpiration();
    }

    private Claims getClaimsFromToken(String token, String publicKey) {
        try {
            return Jwts.parser().setSigningKey(getRSAPublicKey(publicKey)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            logger.error("parse token fail, token:[{}], err:[{}]", token, Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getKey(String keyName) {
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("privateKey", "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCO6w6O4nP72r/wjihbg7siqnukGVleBpVRdJjOeLgLyF/FUx8KL3zyED0IqR06SR+IRFp6qB+gbgFJ4fV17h9jSVRj/jngGxVK4sqEvIHk/70Q5plU0jd8pJWIcp9r0uXgHPCT0HXbOhQLxFB5YO44Awm3XhJdsuCWZLgsHsObQP1jK/lgYKYHsyOUwjeRdixxVHB+FS2TJRHNVFCPFgCDIpQvFt/przswRTlCQ2bSXZ8xaeOreLmPlvE7ap+9eyzK9s5gct7+eP4EKcRjy3lTWg9Uaz9LlM2ZyMkLAjRfyihVkrUrGsW0mqEPK2SA1NvAOpV5VwKm3ivfLmjL4VZ5AgMBAAECggEALhm8dHKvnwoM5/ezJPH1J1V+1Iwou5+8Aq5trAxcsdBMtEX/uTVgRGTgiy6h8u8rcLU7ylz49rMFT2PH3eaV0V0SD78txBB4adOGKBL2g3vK7Oy1gRreMyKT6ZOvwQQiioxLPiL8IV82oCUPIFgX/5FbTP7XRcoxj9GlZvX1etqb7u0amGqIp2T26M6PuCS+fcVouOCb4bkpBrobleWnuRmb4iLLgSPJ7lCkUpw3SXwqJk8lgmlkPBd0EQuwfENy3qP1dTN2R5WC1lHDg06vQ/5YmFwER+C/WdWU+6E9ZCuB+W2TY5JIPn8jcjRN8SxjWxe8Yf9V7MFeaQSuO83qQQKBgQDSKC5Eq6+jUsGuEM4XlV1lbt8taLlc3jjrmOho1QOoSKSCNM/dir19x7YhGjBmeC03dGpDuIPIlMZvA0n4XJn41F+W3Wn/HM0v0j57IDgvMCibhhXRtF14qAONF8rycEvWoB764xPXNP1I/V4Nfg6tDq0ULGm3BLkbLcG9A4/HrQKBgQCuGA3RUJpUAzN0Ftnpv7ARn6TMhotfhmiOQMivrtGcTOIVteXFud9w8j1k8P4Z6FDQuVmUA6IUIOQsSE8i/sqkEv14Z/I2HU1R83lV5MDvt8jRKeA/Fy3xqWQtQiT708aEBotbIiCnq9jNtiiY3rUTuCbFuUnQmObIZKjjxacTfQKBgFCTFUJA7SNabGW3jbzfCKMGQk0ftMoVbY9u3dSrry5LQ7+kTfor4oEiHANPDjWLaBK18LQsP2KdIAOaKDcwEdykWE34cFZejZEDM+9zJ/d3qGoQm7b+R0Qmbs4ezDHtujX8yEP64jWF3IKqme/kX+4cDWIJsTJ5fhscjQZSIkM1AoGAffkN1o4affwq+S+n0wpPnxEDsLMSmrapZXXfQbCgIBdJ6SfL8b1Dv3ipkCcJ1zlRcRcNiJrwZtw9/wT7rnjrrLnJC507mIivcENbYxm5ujwkZaWFjGF7b0iBInagSFC/br1FjVYuhuVEZeYPjj5ojLJN6/jdPsHFDPA5fUeCVAkCgYEAn1fUz+tVYGiGdsapZxnY/pKdqeBLMoc+aaoOUs0b/opRe7PdCVq+8jtk3y4Iw9I+1E9+jPcaluCdnxM5snKnIcO/Nwv7yzY4S83PVNaR1mfNe0OAf7ZVdywLrt6ofm/Hu2qHkg7dOBNeFpAc6terO8nevyDqG9wiDFLpK3Gt0GY=");
        keyMap.put("publicKey", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjusOjuJz+9q/8I4oW4O7Iqp7pBlZXgaVUXSYzni4C8hfxVMfCi988hA9CKkdOkkfiERaeqgfoG4BSeH1de4fY0lUY/454BsVSuLKhLyB5P+9EOaZVNI3fKSViHKfa9Ll4Bzwk9B12zoUC8RQeWDuOAMJt14SXbLglmS4LB7Dm0D9Yyv5YGCmB7MjlMI3kXYscVRwfhUtkyURzVRQjxYAgyKULxbf6a87MEU5QkNm0l2fMWnjq3i5j5bxO2qfvXssyvbOYHLe/nj+BCnEY8t5U1oPVGs/S5TNmcjJCwI0X8ooVZK1KxrFtJqhDytkgNTbwDqVeVcCpt4r3y5oy+FWeQIDAQAB");
        return keyMap.get(keyName);
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @param token 令牌
     * @return 用户对象
     */
    public User getUserFromToken(String token, String publicKey) {
        User user = null;
        if (StringUtils.isNotBlank(token)) {
            // 上下文中Authentication为空
            Claims claims = getClaimsFromToken(token, publicKey);
            if (claims == null || StringUtils.isBlank(claims.getSubject()) || null == claims.get(USER_OBJECT)) {
                logger.info("Get User From Token return null,reason:claims is empty");
                return null;
            }
            if (!validateToken(token, publicKey)) {
                logger.info("Get User From Token return null,reason:token is expired");
                return null;
            }
            user = JSON.parseObject(JSON.toJSONString(claims.get(USER_OBJECT)),User.class);
            logger.info("Get User From Token return  userObject is:{}", JSON.toJSONString(user));
        }
        return user;
    }

    /**
     * 获取 RSAPrivateKey
     *
     * @param priKey base64加密私钥
     */
    private RSAPrivateKey getRSAPrivateKey(String priKey) {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(priKey));
        RSAPrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            logger.error("get RSAPrivateKey fail ,priKey:[{}], err:[{}]", priKey, Throwables.getStackTraceAsString(e));
        }
        return privateKey;
    }

    /**
     * 获取 RSAPublicKey
     *
     * @param pubKey base64加密公钥
     */
    private RSAPublicKey getRSAPublicKey(String pubKey) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKey));
        RSAPublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            logger.error("get RSAPublicKey fail ,pubKey:[{}] ,err:[{}]", pubKey, Throwables.getStackTraceAsString(e));
        }
        return publicKey;
    }
}
