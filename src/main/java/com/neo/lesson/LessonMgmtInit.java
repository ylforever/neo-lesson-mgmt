package com.neo.lesson;

import com.elon.base.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 程序初始化
 *
 * @author neo
 * @since 2025/3/25
 * @version 1.0
 */
@Component
public class LessonMgmtInit implements ApplicationRunner {
    @Value("${neo.jwt.secret:}")
    private String secret;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 设置JWT加密密钥
        JWTUtil.instance().setSecret(secret);
    }
}
