package com.ittxf.securitydemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    // private final DBUserDetailsManager dbUserDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    /*@Override
    public void saveUserDetails(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        dbUserDetailsManager.createUser(userDetails);
    }*/

    @Override
    public boolean save(User user) {
        // 1. 校验用户名是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(wrapper);
        if (count > 0) throw new RuntimeException("用户名已存在");

        // 2. 组装 Entity 实体（直接造 User，不造 UserDetails）
        User userEnt = new User();
        userEnt.setUsername(user.getUsername());
        userEnt.setPassword(passwordEncoder.encode(user.getPassword())); // 加密
        userEnt.setEnabled(true);

        // 3. 直接插入数据库
        return userMapper.insert(userEnt) > 0;
    }
}
