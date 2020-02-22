package com.alena.jewelryproject.mvc.controller.shop;

import com.alena.jewelryproject.mvc.controller.base.ImageHelper;
import com.alena.jewelryproject.mvc.model.Jewelry;
import com.alena.jewelryproject.mvc.service.JewelryService;
import com.alena.jewelryproject.mvc.service.OrderService;
import com.alena.jewelryproject.spring.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.alena.jewelryproject.mvc.controller.base.ControllerHelper.getContextPath;

@Controller
@RequestMapping("/jewelry")
public class JewelryController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShoppingCart shoppingCart;

    @RequestMapping(method = RequestMethod.GET)
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

    @RequestMapping(value = "/addInOrder", method = RequestMethod.POST)
    public ModelAndView addInOrder(HttpServletRequest request,
                                   @RequestParam(value = "id") Long id) {
        orderService.updateOrderAfterAddJewelry(shoppingCart.getOrder(), id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order", shoppingCart.getOrder());
        modelAndView.setViewName("redirect:/jewelry?id=" + id);
        return modelAndView;
    }
}
