package com.ittxf.securitydemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // String localizedMessage = authException.getLocalizedMessage();

        String localizedMessage = "认证失败，需要登录"; // Full authentication is required to access this resource

        HashMap result = new HashMap();
        result.put("code", -1);
        result.put("message", localizedMessage);

        // 3. 转换成 JSON 字符串（Jackson 核心 API）
        String json = objectMapper.writeValueAsString(result);
        // 4. 返回响应
        response.setContentType("application/json;charset=utf-8");
        // 用 write 而不是 println，避免多出无意义的换行符
        response.getWriter().write(json);
    }
}
