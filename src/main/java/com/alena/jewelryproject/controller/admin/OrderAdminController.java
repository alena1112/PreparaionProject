package com.alena.jewelryproject.controller.admin;

import com.alena.jewelryproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/order")
public class OrderAdminController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView getAllOrders() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderList", orderService.getAllOrders());
        modelAndView.setViewName("admin/order_list");
        return modelAndView;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrder(@RequestParam(value = "id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
