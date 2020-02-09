package com.alena.jewelryproject.mvc.controller.shop;

import com.alena.jewelryproject.mvc.controller.base.ImageHelper;
import com.alena.jewelryproject.mvc.model.Jewelry;
import com.alena.jewelryproject.mvc.model.Order;
import com.alena.jewelryproject.mvc.service.JewelryService;
import com.alena.jewelryproject.mvc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.alena.jewelryproject.mvc.controller.base.ControllerHelper.getContextPath;

@Controller
@RequestMapping("/jewelry")
@SessionAttributes(value = "order")
public class JewelryController extends BaseController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getJewelry(HttpServletRequest request,
                                   @RequestParam(value = "id") Long id) {
        Order order = getOrderFromSession(request);
        ModelAndView modelAndView = new ModelAndView();
        Jewelry jewelry = jewelryService.getJewelry(id);
        modelAndView.addObject("jewelry", jewelry);
        modelAndView.addObject("imageHelper",
                new ImageHelper(jewelry, getContextPath(request)));
        modelAndView.addObject("order", order);
        modelAndView.addObject("isContains",
                order.getJewelries()
                        .stream()
                        .anyMatch(j -> j.getId().equals(jewelry.getId()))
        );
        modelAndView.setViewName("shop/jewelry");
        return modelAndView;
    }

    @RequestMapping(value = "/addInOrder", method = RequestMethod.POST)
    public ModelAndView addInOrder(HttpServletRequest request,
                                   @RequestParam(value = "id") Long id) {
        Order order = getOrderFromSession(request);
        orderService.updateOrderAfterAddJewelry(order, id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order", order);
        modelAndView.setViewName("redirect:/jewelry?id=" + id);
        return modelAndView;
    }
}
