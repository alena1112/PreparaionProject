package com.alena.jewelryproject.mvc.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.alena.jewelryproject.mvc.service.SettingKeys.ADMIN_INFO_EMAIL;

@Service
public class SendingEmailService {
    private static final Logger log = LoggerFactory.getLogger(SendingEmailService.class);
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SettingsService settingsService;

    public void sendMessageToAdmin(String message) {
        String toAddress = getAdminAddress();
        if (StringUtils.isBlank(toAddress)) {
            log.warn("Could not send email to admin: admin email address not found");
            return;
        }
        sendEmail(toAddress, message);
    }

    public void sendMessageToClient(String toAddress, String message) {
        sendEmail(toAddress, message);
    }

    private void sendEmail(String toAddress, String message) {
        String fromAddress = getAdminAddress();
        if (StringUtils.isBlank(fromAddress)) {
            log.warn("Could not send email to admin: admin email address not found");
            return;
        }

        SimpleMailMessage emailObj = new SimpleMailMessage();
        emailObj.setFrom(fromAddress);
        emailObj.setTo(toAddress);
        emailObj.setSubject("Graceful Jewelry");
        emailObj.setText(message);
        mailSender.send(emailObj);
        log.info("Email was sent successfully");
    }

    private String getAdminAddress() {
        return settingsService.getSettingByKey(ADMIN_INFO_EMAIL);
    }
}
