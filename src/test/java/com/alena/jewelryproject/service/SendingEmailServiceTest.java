package com.alena.jewelryproject.service;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

import static com.alena.jewelryproject.service.SettingKeys.ADMIN_INFO_EMAIL;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;

@Ignore
@RunWith(SpringRunner.class)
public class SendingEmailServiceTest {
    @Autowired
    private SendingEmailService sendingEmailService;
    @Autowired
    private JavaMailSender mailSender;
    @MockBean
    private SettingsService settingsService;
    @Rule
    public final OutputCapture outputCapture = new OutputCapture();

    @TestConfiguration
    static class SendingEmailServiceTestContextConfiguration {
        @Bean
        public SendingEmailService sendingEmailService() {
            return new SendingEmailService();
        }

        @Bean
        public JavaMailSender mailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setUsername("admin@gracefuljewelry.ru");
            mailSender.setPassword("fjWk85na[#)E,vQd[mshbahr");

            Properties properties = mailSender.getJavaMailProperties();
            properties.put("mail.smtp.host", "smtp.mail.ru");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.debug", "true");

            return mailSender;
        }
    }

    @Test
    public void sendMessageToAdminTest() {
        given(settingsService.getSettingByKey(ADMIN_INFO_EMAIL)).willReturn("admin@gracefuljewelry.ru");
        sendingEmailService.sendMessageToAdmin("Test message");
        outputCapture.expect(containsString("Email was sent successfully"));
    }

    @Test
    public void sendMessageToClientTest() {
        given(settingsService.getSettingByKey(ADMIN_INFO_EMAIL)).willReturn("admin@gracefuljewelry.ru");
        sendingEmailService.sendMessageToClient("kozyafka1112@mail.ru", "Test message");
        outputCapture.expect(containsString("Email was sent successfully"));
    }
}
