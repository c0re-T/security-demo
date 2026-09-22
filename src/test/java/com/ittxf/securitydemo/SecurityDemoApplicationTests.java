package com.ittxf.securitydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SecurityDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testPasswordEncoding() {
        // 工作因子，默认值是10，最小值是4，最大值是31，值越大运算速度越慢
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        // 明文："password"
        // 密文：result，即使明文密码相同，每次生成的密文也不一致
        String result = encoder.encode("password");
        System.out.println(result);

        // 密码校验
        boolean matches = encoder.matches("password", result);
        if (matches) {
            System.out.println("密码匹配");
        } else {
            System.out.println("密码不匹配");
        }
    }

}
