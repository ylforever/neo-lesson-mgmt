package com.neo.lesson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动程序
 */
@SpringBootApplication
public class StartLessonMgmtApp {
    private static final Logger LOGGER = LogManager.getLogger(StartLessonMgmtApp.class);

    public static void main(String[] args) {
        SpringApplication.run(StartLessonMgmtApp.class);
        LOGGER.info("Start Lesson Mgmt success!");
    }
}
