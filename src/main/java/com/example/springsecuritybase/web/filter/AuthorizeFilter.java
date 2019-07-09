package com.example.springsecuritybase.web.filter;

import com.example.springsecuritybase.configuration.MyAccessDeniedHandler;
import com.example.springsecuritybase.exception.ValidateCodeException;
import com.example.springsecuritybase.model.SmsCode;
import com.example.springsecuritybase.model.SysPermission;
import com.example.springsecuritybase.web.controller.SmsValidateCodeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-14
 */
//@Component
//public class AuthorizeFilter extends OncePerRequestFilter {
//
//    private static final List<String> ignoreUrls = Arrays.asList("/login", "/index");
//
//    @Autowired
//    private MyAccessDeniedHandler myAccessDeniedHandler;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        try {
//            validate(auth, request);
//        } catch (AccessDeniedException e) {
//            myAccessDeniedHandler.handle(request, response, e);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private void validate(Authentication auth, HttpServletRequest request) {
//        if (auth instanceof UsernamePasswordAuthenticationToken  && !ignoreUrls.contains(request.getRequestURI())) {
//            String url = request.getRequestURI();
//            String method = request.getMethod();
//
//            boolean isAllowAccess = false;
//            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//            if (CollectionUtils.isEmpty(authorities)) {
//                isAllowAccess = false;
//            }
//
//            for (GrantedAuthority authority : authorities) {
//                SysPermission permission = (SysPermission) authority;
//                String urlForPermission = permission.getUrl();
//                String methodForPermission = permission.getMethod();
//
//                if (url.equals(urlForPermission) && method.equals(methodForPermission)) {
//                    isAllowAccess = true;
//                    break;
//                }
//            }
//
//            if (!isAllowAccess) {
//                throw new AccessDeniedException("Access Denied");
//            }
//        }
//    }
//}
