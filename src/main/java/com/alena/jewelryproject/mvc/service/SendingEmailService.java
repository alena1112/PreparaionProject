package com.alena.jewelryproject.mvc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import static com.alena.jewelryproject.mvc.service.SettingKeys.ADMIN_INFO_EMAIL;

@Service
public class SendingEmailService {
    private static final Logger log = LoggerFactory.getLogger(SendingEmailService.class);

    @Autowired
    private SettingsService settingsService;

    public void sendEmailToClient(String message, String address) {

    }

    public void sendMessageToAdmin(String message) {
        String email = settingsService.getSettingByKey(ADMIN_INFO_EMAIL);
        if (email != null) {
            SimpleMailMessage emailObj = new SimpleMailMessage();
            emailObj.setTo(email);
            emailObj.setSubject("Admin");
            emailObj.setText("Order was created");
            log.info("Email to admin was sent successfully");
        } else {
            log.warn("Could not send email to admin: email address not found");
        }
    }
}
