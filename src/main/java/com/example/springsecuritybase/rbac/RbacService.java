package com.example.springsecuritybase.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-07-09
 */
public interface RbacService {
    /** 判断用户有没有权限访问该请求 */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
