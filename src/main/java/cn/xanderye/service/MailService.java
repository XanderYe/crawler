package cn.xanderye.service;

/**
 * @author XanderYe
 * @description:
 * @date 2021/5/8 17:02
 */
public interface MailService {

    void sendMail(String to, String title, String content);
}
