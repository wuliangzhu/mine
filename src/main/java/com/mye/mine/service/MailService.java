package com.mye.mine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * 邮件服务
 */

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class MailService {
    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    JavaMailSender jms;

    /**
     * 简单的发送邮件
     *
     * @param to 收件人
     * @param title 邮件标题
     * @param content 邮件内容
     * @return
     */
    public void send(String to, String title, String content) {
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom(this.from);
        //接收者
        mainMessage.setTo(to);
        //发送的标题
        mainMessage.setSubject(title);
        //发送的内容
        mainMessage.setText(content);

        jms.send(mainMessage);
    }

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param title
     * @param content
     * @param files
     */
    public void sendAttachmentsMail(String to, String title, String content, String[] files) {
        String[] fileArray = files;

        MimeMessage message = jms.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(this.from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content);

            //验证文件数据是否为空
            if (null != fileArray) {
                FileSystemResource file = null;
                for (int i = 0; i < fileArray.length; i++) {
                    //添加附件
                    file = new FileSystemResource(fileArray[i]);
                    int index = fileArray[i].lastIndexOf(File.separator);
                    helper.addAttachment(fileArray[i].substring(index < 0?0:index), file);
                }
            }
            jms.send(message);

            logger.info("带附件的邮件发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送带附件的邮件失败", e);
        }
    }
}
