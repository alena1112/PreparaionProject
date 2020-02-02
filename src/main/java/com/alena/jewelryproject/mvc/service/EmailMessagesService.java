package com.alena.jewelryproject.mvc.service;

import com.alena.jewelryproject.dao.EmailMessagesDao;
import com.alena.jewelryproject.mvc.model.EmailMessage;
import com.alena.jewelryproject.mvc.model.Order;
import com.alena.jewelryproject.mvc.model.enums.EmailMessageToType;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class EmailMessagesService {
    @Autowired
    private EmailMessagesDao emailMessagesDao;
    @Autowired
    private SendingEmailService sendingEmailService;
    @Autowired
    private OrderService orderService;

    private final Map<String, Function<Order, String>> messageParameters = ImmutableMap.of(
            "${order.userData.firstName}",
            order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getFirstName(), "") : "",
            "${order.userData.lastName}",
            order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getLastName(), "") : ""
    );

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

    public void sendEmail(Long emailId, String toEmail, Long orderId) {
        EmailMessage emailMessage = getEmailMessage(emailId);
        Order order = orderService.getOrder(orderId);
        if (emailMessage != null && order != null) {
            String message = parseMessageWithOrder(emailMessage.getMessage(), order);
            if (emailMessage.getType() == EmailMessageToType.ADMIN) {
                sendingEmailService.sendMessageToAdmin(message);
            } else {
                sendingEmailService.sendMessageToClient(toEmail, message);
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
