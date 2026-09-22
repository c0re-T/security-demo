package com.ittxf.securitydemo.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.ittxf.securitydemo.entity.User;
import com.ittxf.securitydemo.mapper.UserMapper;
import com.ittxf.securitydemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
