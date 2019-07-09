package com.example.springsecuritybase.service;

import com.example.springsecuritybase.model.SysPermission;
import com.example.springsecuritybase.model.SysUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-10
 */
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息和权限
        SysUser user = queryUserInfo(username, null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(user.getUsername(), user.getPassword(), user.getSysPermissions());
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        // 根据用户名查询用户信息和权限
        SysUser user = queryUserInfo(null, userId);
        if (user == null) {
            throw new UsernameNotFoundException(userId);
        }

        return new SocialUser(userId, user.getPassword(), true, true, true, true, user.getSysPermissions());
    }

    public SysUser queryUserInfo(String username, String userId) {
        // 从数据库中查询用户的信息，包括用户的权限
        SysUser user = new SysUser(1L, "admin", new BCryptPasswordEncoder().encode("123456"), Arrays.asList(
                new SysPermission(1L, "用户列表", "user:view", "/getUserList", "GET"),
                new SysPermission(2L, "添加用户", "user:add", "/addUser", "POST"),
                new SysPermission(3L, "修改用户", "user:update", "/updateUser", "PUT")
        ));

        return user;
    }
}
