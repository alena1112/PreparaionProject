package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.EmailsLogRepository;
import com.alena.jewelryproject.model.EmailsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailsLogService {
    @Autowired
    private EmailsLogRepository emailsLogRepository;

    public void save(String message, String from, String to) {
        emailsLogRepository.save(new EmailsLog(message, from, to));
    }
}
