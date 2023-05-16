package com.cyber.authing.granter.normal;

import com.cyber.authing.entity.domain.Account;
import com.cyber.authing.entity.domain.User;
import com.cyber.authing.entity.dto.CyberUserDetails;
import com.cyber.authing.service.AccountService;
import com.cyber.authing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/5
 */
@Service
public class NormalUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.selectOne(Account.builder().account(username).build());
        // 用户不存在，抛出异常
        if (account == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 获取用户信息
        User queryUser = User.builder().build();
        queryUser.setId(account.getUserId());
        User user = userService.selectOne(queryUser);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new CyberUserDetails(account, user, null);
    }
}
