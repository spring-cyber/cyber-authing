package com.cyber.authing.entity.dto;

import com.cyber.authing.entity.domain.Account;
import com.cyber.authing.entity.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/9
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends User {

    public UserDTO(User user) {
        BeanUtils.copyProperties(user, this);
    }

    private String token;

    private List<Account> accountList;
}
