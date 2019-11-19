package com.alena.preparationproject.web.controller;

import com.alena.preparationproject.web.FormatHelper;
import com.alena.preparationproject.web.model.Order;
import com.alena.preparationproject.web.service.OrderService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@SessionAttributes(value = "order")
@RequestMapping("/buy")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries(@ModelAttribute("order") Order order) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/buy");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ModelAndView createOrder(@ModelAttribute("order") Order order) {
        orderService.saveOrder(order);
        ModelAndView modelAndView = new ModelAndView("redirect:/buy");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public @ResponseBody String deleteItem(@ModelAttribute("order") Order order,
                                           @RequestParam("itemId") Long jewelryId) throws IOException {
        orderService.updateOrderAfterDeleteJewelry(order, jewelryId);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(order));
    }

    @RequestMapping(value = "/checkPromoCode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String checkPromoCode(@ModelAttribute("order") Order order,
                                               @RequestParam("code") String code) throws IOException {
        orderService.updateOrderAfterAddPromoCode(order, code);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(order));
    }

    @RequestMapping(value = "/checkDelivery", method = RequestMethod.GET)
    public @ResponseBody String checkDelivery(@ModelAttribute("order") Order order,
                                              @RequestParam("type") String type) throws IOException {
        orderService.updateOrderAfterChangeDeliveryType(order, type);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(order));
    }

    @ModelAttribute("order")
    public Order createOrder() {
        return orderService.createDefaultOrder();
    }

    private ResponseMessage createResponseMessage(Order order) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setValidPromocode(order.getPromocode() != null);
        responseMessage.setFormatPromocode(FormatHelper.getPriceFormat(order.getDiscount()));
        responseMessage.setFormatDeliveryPrice(FormatHelper.getPriceFormat(order.getDeliveryCost()));
        responseMessage.setFormatTotalCost(FormatHelper.getPriceFormat(order.getTotalCost()));
        responseMessage.setFormatCostWithoutDiscount(FormatHelper.getPriceFormat(order.getCostWithoutDiscount()));
        return responseMessage;
    }
}
