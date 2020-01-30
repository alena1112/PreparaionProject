package com.alena.jewelryproject.mvc.controller.admin;

import com.alena.jewelryproject.mvc.service.MaterialService;
import com.alena.jewelryproject.mvc.service.OrderService;
import com.alena.jewelryproject.mvc.service.SettingsService;
import com.alena.jewelryproject.mvc.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView getAllOrders() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderList", orderService.getAllOrders());
        modelAndView.setViewName("admin/order_list");
        return modelAndView;
    }

    @RequestMapping(value = "/material", method = RequestMethod.GET)
    public ModelAndView getAllMaterials() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("materialList", materialService.getAllMaterials());
        modelAndView.setViewName("admin/material_list");
        return modelAndView;
    }

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public ModelAndView getAllShops() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("shopList", shopService.getAllShops());
        modelAndView.setViewName("admin/shop_list");
        return modelAndView;
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public ModelAndView getAllSettings() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("settingsList", settingsService.getAllSettings());
        modelAndView.setViewName("admin/settings_list");
        return modelAndView;
    }
}
