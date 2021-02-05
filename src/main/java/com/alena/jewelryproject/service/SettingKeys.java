package com.alena.jewelryproject.service;

public interface SettingKeys {
    String DELIVERY_COST_RUSSIA_POST_OFFICE = "delivery.cost.russiaPostOffice";
    String DELIVERY_COST_UKRAINE_POST_OFFICE = "delivery.cost.ukrainePostOffice";
    String DELIVERY_COST_KAZAKHSTAN_POST_OFFICE = "delivery.cost.kazakhstanPostOffice";
    String BOXBERRY_MOSCOW_DELIVERY_COST = "delivery.cost.boxberryMoscow";
    String BOXBERRY_MOSCOW_DELIVERY_AVAILABLE = "delivery.available.boxberryMoscow";
    String DELIVERY_PICKUP_AVAILABLE_MOSCOW = "delivery.pickup.available.moscow";
    String DELIVERY_PICKUP_AVAILABLE_SAMARA = "delivery.pickup.available.samara";

    String ADMIN_INFO_EMAIL = "email.admin.info";
    String NEW_ORDER_ADMIN_EMAIL_ID = "email.id.admin.newOrder";
    String NEW_ORDER_CLIENT_EMAIL_ID = "email.id.client.newOrder";

    String NEW_ORDER_CLIENT_EMAIL_WITHOUT_PAYMENT_ID = "newOrder.client.emailWithoutPayment.id";
}
