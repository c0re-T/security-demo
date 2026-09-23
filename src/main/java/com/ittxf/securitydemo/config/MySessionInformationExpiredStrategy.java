package com.ittxf.securitydemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    private final ObjectMapper objectMapper;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {

        // 创建结果对象（建议加上泛型，避免编译警告）
        HashMap result = new HashMap();
        result.put("code", -1);
        result.put("message", "该账号已在其他设备登录");

        // 转换成 JSON 字符串（Jackson 核心 API）
        String json = objectMapper.writeValueAsString(result);

        // 获取响应对象
        HttpServletResponse response = event.getResponse();

        // 返回响应
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
