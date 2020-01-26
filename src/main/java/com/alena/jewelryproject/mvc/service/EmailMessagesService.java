package com.alena.jewelryproject.mvc.service;

import com.alena.jewelryproject.dao.EmailMessagesDao;
import com.alena.jewelryproject.mvc.model.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailMessagesService {
    @Autowired
    private EmailMessagesDao emailMessagesDao;

    public List<EmailMessage> getAllEmailMessages() {
        return emailMessagesDao.getAll();
    }

    public EmailMessage getEmailMessage(Long id) {
        return emailMessagesDao.get(id).orElse(null);
    }

    public void save(EmailMessage emailMessage) {
        emailMessagesDao.save(emailMessage);
    }

    public void update(EmailMessage emailMessage) {
        emailMessagesDao.update(emailMessage);
    }

    public void deleteEmailMessage(Long id) {
        emailMessagesDao.delete(id);
    }
}
