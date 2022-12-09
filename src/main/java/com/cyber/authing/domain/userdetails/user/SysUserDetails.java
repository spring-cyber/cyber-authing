package com.cyber.authing.domain.userdetails.user;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.cyber.authing.domain.constant.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @description: 系统管理用户认证信息
 * @author: snow4aa
 * @create: 2022-08-07 16:29
 */
@Data
public class SysUserDetails implements UserDetails {

    private UserAuthDTO user;

    /**
     * 扩展字段：认证身份标识，枚举值如下：
     */
    private String authenticationIdentity;
    private Collection<SimpleGrantedAuthority> authorities;

    /**
     * 系统管理用户
     */
    public SysUserDetails() {
//        this.user = user;
        user = new UserAuthDTO();
        user.setId(1L);
        user.setPassword("123");
        user.setRoles(Arrays.asList("ADMIN","USER"));
        if (CollectionUtil.isNotEmpty(user.getRoles())) {
            authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public Long getUserId() {
        return user.getId();
    }

    public List<String> getRoles() {
        return user.getRoles();
    }

    @Override
    public String getUsername() {
        return "user.getNickName()";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return SecurityConstants.STATUS_YES.equals(1);
    }
}
