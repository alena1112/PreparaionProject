package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.OrderRepository;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.Order;
import com.alena.jewelryproject.model.PromotionalCode;
import com.alena.jewelryproject.model.enums.DeliveryType;
import com.alena.jewelryproject.model.enums.PromoCodeType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.alena.jewelryproject.service.SettingKeys.DELIVERY_COST_RUSSIA_POST_OFFICE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private PromoCodeService promoCodeService;
    @MockBean
    private JewelryService jewelryService;
    @MockBean
    private SettingsService settingsService;
    @MockBean
    private EmailMessagesService emailMessagesService;
    @MockBean
    private OrderRepository orderRepository;

    @TestConfiguration
    static class OrderServiceTestContextConfiguration {
        @Bean
        public OrderService orderService(OrderRepository orderRepository, PromoCodeService promoCodeService, JewelryService jewelryService,
                                         SettingsService settingsService, EmailMessagesService emailMessagesService) {
            return new OrderService(orderRepository, promoCodeService, jewelryService, settingsService, emailMessagesService);
        }
    }

    @Test
    public void saveOrder() {
        Jewelry gracefulWhite = new Jewelry();
        gracefulWhite.setId((long) 1);
        gracefulWhite.setName("Graceful White");
        gracefulWhite.setPrice(1000.0);

        Jewelry gracefulGold = new Jewelry();
        gracefulGold.setId((long) 2);
        gracefulGold.setName("Graceful Gold");
        gracefulGold.setPrice(2500.0);

        PromotionalCode promotionalCode = new PromotionalCode();
        promotionalCode.setCode("pc");
        promotionalCode.setActive(true);
        promotionalCode.setPromoCodeType(PromoCodeType.PERCENT);
        promotionalCode.setMaxJewelries(1);
        promotionalCode.setMaxUsesNumber(1);
        promotionalCode.setCurrentUsesNumber(0);

        double deliveryCost = 200.0;

        Order order = new Order();
        order.setJewelries(Arrays.asList(gracefulWhite, gracefulGold));
        order.setDeliveryCost(deliveryCost);
        order.setDeliveryType(DeliveryType.POST_OFFICE);
        order.setPromocode(promotionalCode);

        given(jewelryService.getJewelry(gracefulWhite.getId())).willReturn(gracefulWhite);
        given(jewelryService.getJewelry(gracefulGold.getId())).willReturn(gracefulGold);
        given(settingsService.getSettingByKey(DELIVERY_COST_RUSSIA_POST_OFFICE, "50")).willReturn(String.valueOf(deliveryCost));
        given(settingsService.getSettingByKey(SettingKeys.NEW_ORDER_ADMIN_EMAIL_ID)).willReturn(String.valueOf(1));
        given(settingsService.getSettingByKey(SettingKeys.NEW_ORDER_CLIENT_EMAIL_ID)).willReturn(String.valueOf(2));
        try {
            orderService.saveOrder(order);
            verify(promoCodeService).applyPromotionCode(promotionalCode);
            verify(jewelryService).sellJewelries(order.getJewelries());
            verify(orderRepository).save(order);
            verify(emailMessagesService).sendEmail((long) 1, order);
            verify(emailMessagesService).sendEmail((long) 2, order);
        } catch (CreateOrderException e) {
            e.printStackTrace();
        }
    }
}
