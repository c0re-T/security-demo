package com.ittxf.securitydemo.controller;

import com.ittxf.securitydemo.entity.User;
import com.ittxf.securitydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') and authentication.name=='admin'")
    public List<User> getList() {
        return userService.list();
    }

    @PostMapping("/save")
    // @PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasAuthority('USER_ADD')")
    public boolean save(@RequestBody User user) {
        // userService.saveUserDetails(user);
        return userService.saveUser(user);
    }
}
