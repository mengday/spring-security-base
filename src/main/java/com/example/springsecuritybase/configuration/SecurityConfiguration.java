package com.example.springsecuritybase.configuration;

import com.example.springsecuritybase.service.MyUserDetailsService;
import com.example.springsecuritybase.social.qq.MySpringSocialConfigurer;
import com.example.springsecuritybase.support.MyExpiredSessionStrategy;
import com.example.springsecuritybase.support.MyLogoutSuccessHandler;
import com.example.springsecuritybase.web.filter.ImageValidateCodeFilter;
import com.example.springsecuritybase.web.filter.SmsValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-05
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private MySpringSocialConfigurer mySpringSocialConfigurer;

//    @Autowired
//    private AuthorizeFilter authorizeFilter;

    @Autowired
    private DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        ImageValidateCodeFilter validateCodeFilter = new ImageValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        SmsValidateCodeFilter smsValidateCodeFilter = new SmsValidateCodeFilter();
        smsValidateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        http.csrf().disable()
                .apply(mySpringSocialConfigurer).and()
                // 配置需要认证的请求
                .authorizeRequests()
                .antMatchers("/login", "/code/image", "/code/sms", "/session/invalid", "/logout", "/signOut"
                        , "/signup"
                        , "/user/regist").permitAll()
                .anyRequest().access("@rbacServiceImpl.hasPermission(request, authentication)")
//                    .authenticated()
                    .and()
                // 登录表单相关配置
//                .addFilterBefore(smsValidateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(authorizeFilter, ExceptionTranslationFilter.class)
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(myAuthenticationSuccessHandler)
                    .failureHandler(myAuthenticationFailureHandler)
                    .failureUrl("/login?error")
                    .permitAll()
                    .and()
                 .rememberMe()
                    .userDetailsService(myUserDetailsService)
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(60 * 60 * 60 * 30)
                    .and()
                .sessionManagement()
                    .invalidSessionUrl("/session/invalid")
                    .maximumSessions(1)
//                    .maxSessionsPreventsLogin(true)
                    .expiredSessionStrategy(new MyExpiredSessionStrategy())
                    .and()
                .and()
                // 登出相关配置
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(myLogoutSuccessHandler)
                    .deleteCookies("JSESSIONID")
                    .permitAll();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 创建表 create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, "
     * 			+ "token varchar(64) not null, last_used timestamp not null)
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);

        return tokenRepository;
    }

//    @Bean
//    public DynamicallyUrlInterceptor dynamicallyUrlInterceptor() {
//        DynamicallyUrlInterceptor interceptor = new DynamicallyUrlInterceptor();
//        // 1. 设置安全源数据
//        interceptor.setSecurityMetadataSource(new MyFilterInvocationSecurityMetadataSource());
//
//        // 2. 设置访问决定管理器
//        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
//        decisionVoters.add(new RoleVoter());
//        MyAccessDecisionManager accessDecisionManager = new MyAccessDecisionManager(decisionVoters);
//        interceptor.setAccessDecisionManager(accessDecisionManager);
//
//        return interceptor;
//    }
}
