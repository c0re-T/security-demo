package com.ittxf.securitydemo.controller;

import com.ittxf.securitydemo.entity.User;
import com.ittxf.securitydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public List<User> getList() {
        return userService.list();
    }

    @PostMapping("/save")
    public boolean add(@RequestBody User user) {
        // userService.saveUserDetails(user);
        return userService.save(user);
    }
}
