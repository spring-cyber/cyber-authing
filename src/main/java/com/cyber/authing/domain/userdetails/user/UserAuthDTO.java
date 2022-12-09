package com.cyber.authing.domain.userdetails.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: snow4aa
 * @create: 2022-08-07 22:24
 */
@Data
public class UserAuthDTO implements Serializable {

    private Long id;

    private String password;
    /**
     * 用户角色编码集合 ["ROOT","ADMIN"]
     */
    private List<String> roles;

}
