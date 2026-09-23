package com.ittxf.securitydemo.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ittxf.securitydemo.entity.User;
import com.ittxf.securitydemo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {

    private final UserMapper userMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        throw new UnsupportedOperationException("请使用UserService注册用户");
        /*User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(true);
        userMapper.insert(user);*/
    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    /**
     * 从数据库中加载用户
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    true, // 账号是否未过期
                    true, // 用户凭证是否过期
                    true, // 用户是否未锁定
                    new ArrayList<>() // 权限列表
            );
        }else {
            throw new UsernameNotFoundException(username);
        }
    }
}
