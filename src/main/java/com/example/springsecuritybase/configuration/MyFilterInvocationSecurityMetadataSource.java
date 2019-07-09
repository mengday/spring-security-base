package com.example.springsecuritybase.configuration;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-17
 */
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    /**
     * 加载用户的所有权限
     */
    public MyFilterInvocationSecurityMetadataSource() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<RequestMatcher, Collection<ConfigAttribute>> map = new HashMap<>();

        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/getUserList");
        SecurityConfig config = new SecurityConfig("ROLE_user:view");
        ArrayList<ConfigAttribute> configs = new ArrayList<>();
        configs.add(config);
        map.put(matcher,configs);

        AntPathRequestMatcher matcher2 = new AntPathRequestMatcher("/addUser");
        SecurityConfig config2 = new SecurityConfig("ROLE_user:add");
        ArrayList<ConfigAttribute> configs2 = new ArrayList<>();
        configs2.add(config2);
        map.put(matcher2,configs2);

        AntPathRequestMatcher matcher3 = new AntPathRequestMatcher("/updateUser");
        SecurityConfig config3 = new SecurityConfig("ROLE_user:update");
        ArrayList<ConfigAttribute> configs3 = new ArrayList<>();
        configs2.add(config2);
        map.put(matcher2,configs2);

        this.requestMap = map;
    }

    /**
     * 获取当前请求对应的配置
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        HttpServletRequest request = fi.getRequest();
        String url = fi.getRequestUrl();
        String httpMethod = fi.getRequest().getMethod();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
