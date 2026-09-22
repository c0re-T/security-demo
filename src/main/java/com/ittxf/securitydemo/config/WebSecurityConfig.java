package com.ittxf.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // 开启springsecurity的web安全功能（在springboot中可以省略此注解）
public class WebSecurityConfig {

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
                // 对所有请求开启授权保护
                .anyRequest()
                // 已认证的请求会被自动授权
                .authenticated()
        )
        .formLogin(withDefaults()) // 使用表单授权方式
        // .httpBasic(withDefaults()) // 使用基本授权方式
        .csrf(csrf -> csrf.disable()); // 禁用 CSRF 保护
        return http.build();
    }

}
