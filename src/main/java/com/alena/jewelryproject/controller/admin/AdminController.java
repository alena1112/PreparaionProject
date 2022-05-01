package com.alena.jewelryproject.controller.admin;

import com.alena.jewelryproject.model.enums.Shop;
import com.alena.jewelryproject.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = "jewelry")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private MaterialService materialService;

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
        modelAndView.addObject("shopList", Shop.values());
        modelAndView.setViewName("admin/shop_list");
        return modelAndView;
    }
}
