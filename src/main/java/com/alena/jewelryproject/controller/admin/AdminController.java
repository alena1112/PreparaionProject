package com.alena.jewelryproject.controller.admin;

import com.alena.jewelryproject.service.MaterialService;
import com.alena.jewelryproject.service.OrderService;
import com.alena.jewelryproject.service.SettingsService;
import com.alena.jewelryproject.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = "jewelry")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private SettingsService settingsService;

    @GetMapping("/order/list")
    public ModelAndView getAllOrders() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderList", orderService.getAllOrders());
        modelAndView.setViewName("admin/order_list");
        return modelAndView;
    }

    @GetMapping("/material/list")
    public ModelAndView getAllMaterials() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("materialList", materialService.getAllMaterials());
        modelAndView.setViewName("admin/material_list");
        return modelAndView;
    }

    @GetMapping("/shop/list")
    public ModelAndView getAllShops() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("shopList", shopService.getAllShops());
        modelAndView.setViewName("admin/shop_list");
        return modelAndView;
    }
}