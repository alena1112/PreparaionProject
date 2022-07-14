package com.alena.jewelryproject.controller.admin;

import com.alena.jewelryproject.model.enums.Shop;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = "jewelry")
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/shop/list")
    public ModelAndView getAllShops() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("shopList", Shop.values());
        modelAndView.setViewName("admin/shop_list");
        return modelAndView;
    }
}
