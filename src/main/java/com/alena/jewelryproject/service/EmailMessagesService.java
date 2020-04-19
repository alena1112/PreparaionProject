package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.EmailMessagesRepository;
import com.alena.jewelryproject.model.EmailMessage;
import com.alena.jewelryproject.model.Order;
import com.alena.jewelryproject.model.enums.EmailMessageToType;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class EmailMessagesService {
    private static final Logger log = LoggerFactory.getLogger(EmailMessagesService.class);

    @Autowired
    private EmailMessagesRepository emailMessagesRepository;
    @Autowired
    private SendingEmailService sendingEmailService;

    private final Map<String, Function<Order, String>> messageParameters = ImmutableMap.of(
            "${order.userData.firstName}",
            order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getFirstName(), "") : "",
            "${order.userData.lastName}",
            order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getLastName(), "") : ""
    );

    public List<EmailMessage> getAllEmailMessages() {
        return emailMessagesRepository.findAll();
    }

    public EmailMessage getEmailMessage(Long id) {
        return emailMessagesRepository.findById(id).orElse(null);
    }

    public void save(EmailMessage emailMessage) {
        emailMessagesRepository.save(emailMessage);
    }

    public void update(EmailMessage emailMessage) {
        emailMessagesRepository.save(emailMessage);
    }

    public void deleteEmailMessage(Long id) {
        emailMessagesRepository.deleteById(id);
    }

    public void sendEmail(Long emailId, Order order) {
        EmailMessage emailMessage = getEmailMessage(emailId);
        if (emailMessage != null && order != null) {
            String message = parseMessageWithOrder(emailMessage.getMessage(), order);
            if (emailMessage.getType() == EmailMessageToType.ADMIN) {
                sendingEmailService.sendMessageToAdmin(message);
            } else {
                sendingEmailService.sendMessageToClient(order.getUserData().getEmail(), message);
            }
        }
    }

    private String parseMessageWithOrder(String message, Order order) {
        if (StringUtils.isNotBlank(message)) {
            for (Map.Entry<String, Function<Order, String>> entry : messageParameters.entrySet()) {
                String param = entry.getKey();
                if (message.contains(param)) {
                    message = message.replace(param, entry.getValue().apply(order));
                }
            }
        }
        return message;
    }
}
