package cn.xanderye.service.impl;

import cn.xanderye.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author XanderYe
 * @description:
 * @date 2021/5/8 17:02
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    public void sendMail(String to, String title, String content) {
        try {
            log.info("发送邮件，发往：{}；标题：{}；内容：{}", to, title, content);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(this.mailFrom);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(content);
            this.javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
