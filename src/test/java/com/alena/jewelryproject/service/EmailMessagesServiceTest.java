package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.EmailMessagesRepository;
import com.alena.jewelryproject.model.EmailMessage;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.Order;
import com.alena.jewelryproject.model.UserData;
import com.alena.jewelryproject.model.enums.DeliveryType;
import com.alena.jewelryproject.model.enums.EmailMessageToType;
import com.alena.jewelryproject.model.enums.PaymentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class EmailMessagesServiceTest {
    @Autowired
    private EmailMessagesService emailMessagesService;
    @MockBean
    private EmailMessagesRepository emailMessagesRepository;
    @MockBean
    private SendingEmailService sendingEmailService;

    private final String firstToClientMsg = "Ваш заказ ${order.id} принят в обработку. Скоро Вам придет письмо с деталями оплаты.\n" +
            "\n" +
            "Спасибо за оформление заказа в интернет-магазине Graceful Jewelry.\n" +
            "\n" +
            "Состав заказа:\n" +
            "${order.jewelries}\n" +
            "\n" +
            "Сумма:\n" +
            "${order.price}";

    private final String firstToAdminMsg = "Поступил заказ ${order.id} на украшения ${order.jewelries}.\n" +
            "Детали заказа:\n" +
            "Инициалы клиента: ${order.userData.firstName} ${order.userData.lastName}\n" +
            "Адрес клиента: ${order.userData.address}\n" +
            "Город: ${order.userData.city}\n" +
            "Индекс: ${order.userData.postIndex}\n" +
            "Эмейл: ${order.userData.email}\n" +
            "Телефон: ${order.userData.phone}\n" +
            "Тип доставки: ${order.deliveryType}\n" +
            "Тип оплаты: ${order.paymentType}";

    @TestConfiguration
    static class EmailMessagesServiceTestContextConfiguration {
        @Bean
        public EmailMessagesService emailMessagesService() {
            return new EmailMessagesService();
        }
    }

    @Test
    public void sendEmailTestFirstToClientMsg() {
        UserData userData = new UserData();
        userData.setEmail("test@mail.ru");

        Order order = new Order();
        order.setId(1L);
        order.setUserData(userData);

        EmailMessage firstToClient = new EmailMessage();
        firstToClient.setId(1L);
        firstToClient.setType(EmailMessageToType.CLIENT);
        firstToClient.setMessage(firstToClientMsg);

        given(emailMessagesRepository.findById(firstToClient.getId())).willReturn(Optional.of(firstToClient));

        emailMessagesService.sendEmail(firstToClient.getId(), order);

        verify(sendingEmailService).sendMessageToClient(anyString(), anyString());
    }

    @Test
    public void sendEmailTestFirstToAdminMsg() {
        Order order = new Order();
        order.setId(1L);

        EmailMessage firstToAdmin = new EmailMessage();
        firstToAdmin.setId(2L);
        firstToAdmin.setType(EmailMessageToType.ADMIN);
        firstToAdmin.setMessage(firstToAdminMsg);

        given(emailMessagesRepository.findById(firstToAdmin.getId())).willReturn(Optional.of(firstToAdmin));

        emailMessagesService.sendEmail(firstToAdmin.getId(), order);

        verify(sendingEmailService).sendMessageToAdmin(anyString());
    }

    @Test
    public void parseFirstClientMessageWithOrderTest() {
        UserData userData = new UserData();
        userData.setEmail("test@mail.ru");

        Jewelry gracefulWhite = new Jewelry();
        gracefulWhite.setName("Graceful White");

        Jewelry gracefulGold = new Jewelry();
        gracefulGold.setName("Graceful Gold");

        Order order = new Order();
        order.setId(1L);
        order.setUserData(userData);
        order.setJewelries(Arrays.asList(gracefulWhite, gracefulGold));
        order.setTotalCost(2200.0);

        String resultMessage = emailMessagesService.parseMessageWithOrder(firstToClientMsg, order);
        assertTrue(resultMessage.contains("Ваш заказ " + order.getId().toString()));
        assertTrue(resultMessage.contains(gracefulWhite.getName()));
        assertTrue(resultMessage.contains(gracefulGold.getName()));
        assertTrue(resultMessage.contains(order.getTotalCost().toString()));
        assertFalse(resultMessage.contains("${"));
    }

    @Test
    public void parseFirstAdminMessageWithOrderTest() {
        UserData userData = new UserData();
        userData.setEmail("test@mail.ru");
        userData.setFirstName("Иван");
        userData.setLastName("Иванович");
        userData.setAddress("Город Улица Дом");
        userData.setCity("Город");
        userData.setPostIndex("123456");
        userData.setPhone("9 000 000 000");

        Jewelry gracefulWhite = new Jewelry();
        gracefulWhite.setName("Graceful White");

        Jewelry gracefulGold = new Jewelry();
        gracefulGold.setName("Graceful Gold");

        Order order = new Order();
        order.setId(1L);
        order.setUserData(userData);
        order.setJewelries(Arrays.asList(gracefulWhite, gracefulGold));
        order.setTotalCost(2200.0);
        order.setDeliveryType(DeliveryType.RUSSIA_POST_OFFICE);
        order.setPaymentType(PaymentType.TRANSFER_TO_BANK_CARD);

        String resultMessage = emailMessagesService.parseMessageWithOrder(firstToAdminMsg, order);
        assertTrue(resultMessage.contains(gracefulWhite.getName()));
        assertTrue(resultMessage.contains(gracefulGold.getName()));
        assertTrue(resultMessage.contains("Поступил заказ " + order.getId().toString()));
        assertTrue(resultMessage.contains(order.getUserData().getFirstName()));
        assertTrue(resultMessage.contains(order.getUserData().getLastName()));
        assertTrue(resultMessage.contains(order.getUserData().getAddress()));
        assertTrue(resultMessage.contains(order.getUserData().getCity()));
        assertTrue(resultMessage.contains(order.getUserData().getPostIndex()));
        assertTrue(resultMessage.contains(order.getUserData().getEmail()));
        assertTrue(resultMessage.contains(order.getUserData().getPhone()));
        assertTrue(resultMessage.contains(order.getDeliveryType().getName()));
        assertTrue(resultMessage.contains(order.getPaymentType().getName()));
        assertFalse(resultMessage.contains("${"));
    }
}
