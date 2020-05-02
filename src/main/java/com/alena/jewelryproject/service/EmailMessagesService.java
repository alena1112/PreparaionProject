package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.EmailMessagesRepository;
import com.alena.jewelryproject.model.EmailMessage;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.Order;
import com.alena.jewelryproject.model.enums.EmailMessageToType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmailMessagesService {
    private static final Logger log = LoggerFactory.getLogger(EmailMessagesService.class);

    @Autowired
    private EmailMessagesRepository emailMessagesRepository;
    @Autowired
    private SendingEmailService sendingEmailService;

    private final Map<String, Function<Order, String>> messageParameters = new HashMap<String, Function<Order, String>>() {{
        put("${order.userData.firstName}",
                order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getFirstName(), "<имя>") : "<имя>");
        put("${order.userData.lastName}",
                order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getLastName(), "<фамилия>") : "<фамилия>");
        put("${order.id}",
                order -> order.getId().toString());
        put("${order.jewelries}",
                order -> order.getJewelries() != null ? order.getJewelries().stream().map(Jewelry::getName).collect(Collectors.joining(", ")) : "<украшения>");
        put("${order.price}",
                order -> order.getTotalCost() != null ? order.getTotalCost().toString() : "<цена>");
        put("${order.userData.address}",
                order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getAddress(), "<адрес>") : "<адрес>");
        put("${order.userData.city}",
                order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getCity(), "<город>") : "<город>");
        put("${order.userData.postIndex}",
                order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getPostIndex(), "<индекс>") : "<индекс>");
        put("${order.userData.email}",
                order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getEmail(), "<эмейл>") : "<эмейл>");
        put("${order.userData.phone}",
                order -> order.getUserData() != null ? StringUtils.defaultIfBlank(order.getUserData().getPhone(), "<телефон>") : "<телефон>");
        put("${order.deliveryType}",
                order -> order.getDeliveryType() != null ? order.getDeliveryType().getName() : "<способ доставки>");
        put("${order.paymentType}",
                order -> order.getPaymentType() != null ? order.getPaymentType().getName() : "<способ оплаты>");
    }};

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

    public String parseMessageWithOrder(String message, Order order) {
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
