package com.cyber.authing.domain.clientdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

/**
 * @description: OAuth2 客户端信息
 * @author: snow4aa
 * @create: 2022-08-07 16:24
 */
@Service
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        try{
            //TODO 实现授权中心
            return null;
        } catch (Exception var4) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }
}
