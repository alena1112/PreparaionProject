package com.alena.jewelryproject;

import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class SendingEmailServiceTest {

    @Test
    public void sendEmailTest() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setUsername("admin@gracefuljewelry.ru");
//        mailSender.setPassword("gigi1611liop");
//
//        Properties properties = mailSender.getJavaMailProperties();
//        properties.put("mail.smtp.host", "smtp.mail.ru");
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.auth", true);
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.socketFactory.port", "465");
//        properties.put("mail.debug", true);
//
//        SimpleMailMessage emailObj = new SimpleMailMessage();
//        emailObj.setFrom("admin@gracefuljewelry.ru");
//        emailObj.setTo("alena-kov-1992@mail.ru");
//        emailObj.setSubject("Graceful Jewelry");
//        emailObj.setText("test");
//        mailSender.send(emailObj);
    }
}
