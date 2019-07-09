package com.example.springsecuritybase;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-04-22
 */
@RestController
public class ExampleController {



    @GetMapping("helloworld")
    public List<String> helloworld() {
        return Arrays.asList("Spring Security simple demo");
    }


    @GetMapping("hasRole")
    public void hasRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FilterInvocation fi = new FilterInvocation(null, null);
        WebSecurityExpressionRoot webSecurityExpressionRoot = new WebSecurityExpressionRoot(auth, fi);
        boolean b = webSecurityExpressionRoot.hasRole("xxx");
        System.out.println(b);
    }
}
