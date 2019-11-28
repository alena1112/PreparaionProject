package com.alena.preparationproject.mvc.controller.admin;

import com.alena.preparationproject.mvc.model.Jewelry;
import com.alena.preparationproject.mvc.model.enums.JewelryType;
import com.alena.preparationproject.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PromoCodeService promoCodeService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/jewelry/list", method = RequestMethod.GET)
    public ModelAndView getAllJewelries() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryList", jewelryService.getAllJewelries());
        modelAndView.setViewName("admin/jewelry_list");
        return modelAndView;
    }

    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public ModelAndView getAllOrders() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderList", orderService.getAllOrders());
        modelAndView.setViewName("admin/order_list");
        return modelAndView;
    }

    @RequestMapping(value = "/promocode/list", method = RequestMethod.GET)
    public ModelAndView getAllPromocodes() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("promocodeList", promoCodeService.getAllPromocodes());
        modelAndView.setViewName("admin/promocode_list");
        return modelAndView;
    }

    @RequestMapping(value = "/material/list", method = RequestMethod.GET)
    public ModelAndView getAllMaterials() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("materialList", materialService.getAllMaterials());
        modelAndView.setViewName("admin/material_list");
        return modelAndView;
    }

    @RequestMapping(value = "/shop/list", method = RequestMethod.GET)
    public ModelAndView getAllShops() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("shopList", shopService.getAllShops());
        modelAndView.setViewName("admin/shop_list");
        return modelAndView;
    }

    @RequestMapping(value = "/jewelry/edit", method = RequestMethod.GET)
    public ModelAndView editJewelry(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryItem", id != null ? jewelryService.getJewelry(id) : new Jewelry());
        modelAndView.addObject("jewelryTypes", JewelryType.values());
        modelAndView.setViewName("admin/jewelry_edit");
        return modelAndView;
    }

    @RequestMapping(value = "/jewelry/save", method = RequestMethod.POST)
    public ModelAndView saveJewelry(@RequestParam(value = "id") Long id, @ModelAttribute("jewelryItem") Jewelry jewelry) {
        if (id == null) {
            jewelryService.saveNew(jewelry);
        } else {
            jewelry.setId(id);
            jewelryService.save(jewelry);
        }
        return new ModelAndView("redirect:/jewelry/list", new HashMap<>());
    }

    @RequestMapping(value = "/jewelry/delete", method = RequestMethod.DELETE)
    public @ResponseBody String deleteJewelry(@RequestParam(value = "id") Long id) {
//        jewelryService.deleteJewelry(id);
        return "ok";
    }
}
