package com.ittxf.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity // 开启springsecurity的web安全功能（在springboot中可以省略此注解）
public class WebSecurityConfig {

    // 直接把 BCryptPasswordEncoder 作为 Bean 交给 Spring 管理
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 这里就写具体的实现类
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // 创建基于内存的用户信息管理器
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // 向用户信息管理器中添加用户
        manager.createUser(
                // 创建UserDetails对象，用于管理用户名，用户密码，用户角色，用户权限等内容
                User.withUsername("user").password(passwordEncoder().encode("123456")).roles("USER").build()
        );
        return manager;
    }

}
