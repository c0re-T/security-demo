package com.ittxf.securitydemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HashMap result = new HashMap();
        result.put("code", 0);
        result.put("message", "登出成功");

        // 3. 转换成 JSON 字符串（Jackson 核心 API）
        String json = objectMapper.writeValueAsString(result);
        // 4. 返回响应
        response.setContentType("application/json;charset=utf-8");
        // 用 write 而不是 println，避免多出无意义的换行符
        response.getWriter().write(json);
    }
}
