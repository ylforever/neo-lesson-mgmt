package com.neo.lesson.util;

import com.elon.base.constant.EnumThreadTaskType;
import com.elon.base.model.ThreadTaskBase;
import com.neo.lesson.model.Email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 发送email任务执行器
 *
 * @author neo
 * @since 2025-02-24
 */
public class EmailTaskExecutor extends ThreadTaskBase {
    private static Logger LOGGER = LogManager.getLogger(EmailTaskExecutor.class);

    private final String sender;

    private final JavaMailSender javaMailSender;

    private final Email email;

    public EmailTaskExecutor(String taskId, JavaMailSender javaMailSender, Email email, String sender) {
        super(taskId, EnumThreadTaskType.SEND_EMAIL_TYPE);
        this.javaMailSender = javaMailSender;
        this.sender = sender;
        this.email = email;
    }

    @Override
    public void run() {
        try {
            //创建简单邮件消息
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            //谁发的
            helper.setFrom(sender);

            //谁要接收
            String[] receivers = email.getReceiverList().toArray(new String[0]);
            helper.setTo(receivers);

            //邮件标题
            helper.setSubject(email.getTitle());

            //邮件内容
            helper.setText(email.getContent(), true);

            //添加附加文件
            for (File file : email.getAttachFileList()) {
                FileSystemResource resource = new FileSystemResource(file);
                String fileName = resource.getFilename();
                helper.addAttachment(fileName, file);
            }

            javaMailSender.send(message);
            LOGGER.info("Send mail success. taskId:{}|task type:{}|title:{}",  getTaskId(), getTaskType(), email.getTitle());
        }catch (Exception e) {
            LOGGER.error("Send email fail. taskId:{}|task type:{}|title:{}", getTaskId(), getTaskType(), email.getTitle());
        }
    }
}
