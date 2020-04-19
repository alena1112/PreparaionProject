package com.alena.jewelryproject.controller.shop;

import com.alena.jewelryproject.controller.base.ImageHelper;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.service.JewelryService;
import com.alena.jewelryproject.service.OrderService;
import com.alena.jewelryproject.service.sitemap.NeedInSiteMap;
import com.alena.jewelryproject.spring.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.alena.jewelryproject.controller.base.ControllerHelper.getContextPath;

@Controller
@RequestMapping("/jewelry")
public class JewelryController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShoppingCart shoppingCart;

    @NeedInSiteMap(isAllJewelryIds = true)
    @GetMapping
    public ModelAndView getJewelry(HttpServletRequest request,
                                   @RequestParam(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Jewelry jewelry = jewelryService.getJewelry(id);
        modelAndView.addObject("jewelry", jewelry);
        modelAndView.addObject("imageHelper",
                new ImageHelper(jewelry, getContextPath(request)));
        modelAndView.addObject("order", shoppingCart.getOrder());
        modelAndView.addObject("isContains",
                shoppingCart.getOrder().getJewelries()
                        .stream()
                        .anyMatch(j -> j.getId().equals(jewelry.getId()))
        );
        modelAndView.setViewName("shop/jewelry");
        return modelAndView;
    }

    @PostMapping("/addInOrder")
    public ModelAndView addInOrder(HttpServletRequest request,
                                   @RequestParam(value = "id") Long id) {
        orderService.updateOrderAfterAddJewelry(shoppingCart.getOrder(), id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order", shoppingCart.getOrder());
        modelAndView.setViewName("redirect:/jewelry?id=" + id);
        return modelAndView;
    }
}
