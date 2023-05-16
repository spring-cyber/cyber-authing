package com.cyber.authing.entity.dto;

import cn.hutool.core.util.ObjectUtil;
import com.cyber.authing.entity.domain.Account;
import com.cyber.authing.entity.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author yanwei
 * @desc
 * @since 2023/4/15
 */
public class CyberUserDetails implements UserDetails, Serializable {
    private Account account;
    private User user;
    private List<String> permissions;
    private List<SimpleGrantedAuthority> authorities;

    public CyberUserDetails(Account account, User user, List<String> permissions) {
        this.account = account;
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        if (CollectionUtils.isEmpty(permissions)) {
            return null;
        }
        authorities = permissions.stream().filter(x -> !ObjectUtil.isNull(x))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
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
        return true;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CyberUserDetails.class.getSimpleName() + "[", "]")
                .add("username='" + this.getUsername() + "'")
                .add("authorities=" + authorities)
                .add("accountNonExpired=" + this.isAccountNonExpired())
                .add("accountNonLocked=" + this.isAccountNonLocked())
                .add("credentialsNonExpired=" + this.isCredentialsNonExpired())
                .add("enabled=" + this.isEnabled())
                .toString();
    }

    public Account getAccount() {
        return account;
    }

    public User getUser() {
        return user;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
