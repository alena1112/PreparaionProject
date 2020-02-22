package com.alena.jewelryproject.spring;

import com.alena.jewelryproject.mvc.model.Order;
import com.alena.jewelryproject.mvc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart implements Serializable {
    @Autowired
    private OrderService orderService;

    private Order order;

    public Order getOrder() {
        if (order == null) {
            order = orderService.createDefaultOrder();
        }
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
