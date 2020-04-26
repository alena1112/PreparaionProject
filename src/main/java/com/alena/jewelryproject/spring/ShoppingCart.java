package com.alena.jewelryproject.spring;

import com.alena.jewelryproject.model.Order;
import com.alena.jewelryproject.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(ShoppingCart.class);
    @Autowired
    private OrderService orderService;

    private Order order;

    public Order getOrder() {
        if (order == null) {
            order = orderService.createDefaultOrder();
            log.info("Creating new session order");
        }
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        log.info("Change session order: " + (order != null ? order.toString() : "null"));
    }
}
