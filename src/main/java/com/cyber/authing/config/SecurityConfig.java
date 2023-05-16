package com.cyber.authing.config;

import com.cyber.authing.common.filter.JwtTokenFilter;
import com.cyber.authing.granter.normal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

/**
 * @author yanwei
 * @desc
 * @since 2023/4/15
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * 白名单
     */
    @Autowired
    private SecurityWhiteList securityWhiteList;

    @Autowired
    private InvalidAuthenticationEntryPoint invalidAuthenticationEntryPoint;
    @Autowired
    private UsernamePasswordErrorHandler usernamePasswordErrorHandler;
    @Autowired
    private UsernamePasswordSuccessHandler usernamePasswordSuccessHandler;
    @Autowired
    private NormalUserDetailsService normalUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable() //基于token，不做csrf
                .httpBasic().disable() // 禁用basic明文验证
                .formLogin().disable() // 禁用默认登录页
                .logout().disable() // 禁用默认登出页
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// 基于token，不用session
                // 设置异常的EntryPoint，如果不设置，默认使用Http403ForbiddenEntryPoint
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(invalidAuthenticationEntryPoint))
                .authorizeRequests(authorize -> authorize
                        .antMatchers(securityWhiteList.getWhiteList()).permitAll()
                        .antMatchers(HttpMethod.POST, "/register").permitAll()
                        .antMatchers(HttpMethod.POST, "/refreshToken").permitAll()
                        .anyRequest().authenticated())
                .addFilterAt(usernamePasswordFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    UsernamePasswordFilter usernamePasswordFilter() {
        UsernamePasswordFilter filter = new UsernamePasswordFilter();
        ProviderManager manager = new ProviderManager(Collections.singletonList(usernamePasswordProvider()));
        filter.setAuthenticationManager(manager);
        filter.setAuthenticationFailureHandler(usernamePasswordErrorHandler);
        filter.setAuthenticationSuccessHandler(usernamePasswordSuccessHandler);
        return filter;
    }

    @Bean
    UsernamePasswordProvider usernamePasswordProvider() {
        return new UsernamePasswordProvider(normalUserDetailsService, passwordEncoder());
    }
}
