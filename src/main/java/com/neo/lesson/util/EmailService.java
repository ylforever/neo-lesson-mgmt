package com.neo.lesson.util;

import com.elon.base.service.thread.ThreadPoolUtils;
import com.elon.base.util.StringUtil;
import com.neo.lesson.model.Email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 邮件发送服务类
 *
 * @author neo
 * @since 2025-02-22
 */
@Component
public class EmailService {
    private static final Logger LOGGER = LogManager.getLogger(EmailService.class);
    /**
     * 发件人
     */
    @Value("${spring.mail.username:}")
    private String sender;

    @Resource
    private JavaMailSender javaMailSender;

    private ThreadPoolUtils threadPoolUtils = new ThreadPoolUtils(2, 10);

    /**
     * 发送邮件
     *
     * @param email 邮件信息
     */
    public void sendMail(Email email){
        try {
            EmailTaskExecutor emailTaskExecutor = new EmailTaskExecutor(javaMailSender, email, sender);
            threadPoolUtils.executeTask(emailTaskExecutor);
        }catch (Exception e) {
            LOGGER.error("Send email fail.", e);
        }
    }
}
