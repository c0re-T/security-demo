package com.ittxf.securitydemo.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.ittxf.securitydemo.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    void saveUserDetails(User user);
}
