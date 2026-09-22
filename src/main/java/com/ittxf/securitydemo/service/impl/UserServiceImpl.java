package com.ittxf.securitydemo.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.ittxf.securitydemo.config.DBUserDetailsManager;
import com.ittxf.securitydemo.entity.User;
import com.ittxf.securitydemo.mapper.UserMapper;
import com.ittxf.securitydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final DBUserDetailsManager dbUserDetailsManager;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void saveUserDetails(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        dbUserDetailsManager.createUser(userDetails);
    }
}
