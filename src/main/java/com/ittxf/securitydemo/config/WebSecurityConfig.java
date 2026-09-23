package com.ittxf.securitydemo.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // 开启springsecurity的web安全功能（在springboot中可以省略此注解）
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    private final MyLogoutSuccessHandler myLogoutSuccessHandler;
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    private final MySessionInformationExpiredStrategy mySessionInformationExpiredStrategy;
    private final MyAccessDeniedHandler myAccessDeniedHandler;

    // 直接把 BCryptPasswordEncoder 作为 Bean 交给 Spring 管理
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 这里就写具体的实现类
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        // 创建基于内存的用户信息管理器
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // 向用户信息管理器中添加用户
        manager.createUser(
                // 创建UserDetails对象，用于管理用户名，用户密码，用户角色，用户权限等内容
                User.withUsername("user").password(passwordEncoder().encode("123456")).roles("USER").build()
        );
        return manager;
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 开启授权保护
        http.authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/user/list").hasAuthority("USER_LIST") // 具有USER_LIST权限的用户可以访问
                // .requestMatchers("/user/save").hasAuthority("USER_SAVE") // 具有USER_SAVE权限的用户可以访问
                .requestMatchers("/user/**").hasRole("ADMIN") // 具有ADMIN角色的用户可以访问
                // 对所有请求开启授权保护
                .anyRequest()
                // 已认证的请求会被自动授权
                .authenticated()
        )
        // 使用表单授权方式
        .formLogin(form -> form
                .loginPage("/login") // 登录页面的 URL
                .permitAll() // 无需授权访问登录页面
                .usernameParameter("myusername") // 自定义用户名参数名
                .passwordParameter("mypassword") // 自定义密码参数名
                .failureUrl("/login?failure") // 登录失败后的 URL
                .successHandler(myAuthenticationSuccessHandler) // 认证成功后的处理
                .failureHandler(myAuthenticationFailureHandler) // 认证失败后的处理
        )
        .logout(logout -> logout
                .logoutSuccessHandler(myLogoutSuccessHandler) // 登出成功后的处理
        )
        .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(myAuthenticationEntryPoint) // 请求未授权的处理
                .accessDeniedHandler(myAccessDeniedHandler)
                // .accessDeniedHandler(new AccessDeniedHandler() {
                //     @Override
                //     public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                //
                //     }
                // })  // 使用Lambda表达式实现AccessDeniedHandler
        )
        .sessionManagement(session -> session
                .maximumSessions(1) // 最大会话数
                .expiredSessionStrategy(mySessionInformationExpiredStrategy) // 会话过期超出最大数处理
        )
        .cors(withDefaults())
        // .httpBasic(withDefaults()) // 使用基本授权方式
        .csrf(csrf -> csrf.disable()); // 禁用 CSRF 保护
        return http.build();
    }

}
