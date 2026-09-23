package com.ittxf.securitydemo.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.ittxf.securitydemo.entity.User;

public interface UserService extends IService<User> {

    // void saveUserDetails(User user);

    // boolean save(User user); // 可以不用写了这里

    boolean saveUser(User user);
}
