package com.alena.jewelryproject.mvc.controller.shop;

import com.alena.jewelryproject.mvc.model.Order;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected Order getOrderFromSession(HttpServletRequest request) {
        return (Order) request.getSession().getAttribute("order");
    }
}
