package com.alena.preparationproject.web.controller;

import com.alena.preparationproject.web.FormatHelper;
import com.alena.preparationproject.web.model.Order;
import com.alena.preparationproject.web.model.PromotionalCode;
import com.alena.preparationproject.web.model.enums.DeliveryType;
import com.alena.preparationproject.web.service.JewelryService;
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
    private JewelryService jewelryService;
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
        ModelAndView modelAndView = new ModelAndView("redirect:/buy");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public @ResponseBody String deleteItem(@ModelAttribute("order") Order order,
                                           @RequestParam("itemId") Long jewelryId) throws IOException {
        ResponseMessage responseMessage = calculateCostWithJewelries(order, jewelryId);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(responseMessage);
    }

    @RequestMapping(value = "/checkPromoCode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String checkPromoCode(@ModelAttribute("order") Order order,
                                               @RequestParam("code") String code) throws IOException {
        ResponseMessage responseMessage = calculateCostWithPromoCode(order, code);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(responseMessage);
    }

    @RequestMapping(value = "/checkDelivery", method = RequestMethod.GET)
    public @ResponseBody String checkDelivery(@ModelAttribute("order") Order order,
                                              @RequestParam("type") String type) throws IOException {
        ResponseMessage responseMessage = calculateCostWithDelivery(order, type);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(responseMessage);
    }

    private ResponseMessage calculateCostWithJewelries(Order order, Long droppedItemId) {
        order.getJewelries().stream()
                .filter(jewelry -> jewelry.getId().equals(droppedItemId))
                .findFirst()
                .ifPresent(foundJewelry -> order.getJewelries().remove(foundJewelry));

        double allJewelriesPrice = orderService.getAllJewelriesPrice(order.getJewelries());
        order.setDiscount(orderService.getDiscount(allJewelriesPrice, order.getPromocode()));
        order.setTotalCost(orderService.getTotalPrice(
                allJewelriesPrice,
                order.getDiscount(),
                order.getDeliveryCost())
        );
        return createResponseMessage(order);
    }

    private ResponseMessage calculateCostWithDelivery(Order order, String deliveryType) {
        DeliveryType type = DeliveryType.fromId(deliveryType);
        order.setDeliveryType(type);
        order.setDeliveryCost(orderService.getDeliveryPrice(type));
        order.setTotalCost(orderService.getTotalPrice(
                orderService.getAllJewelriesPrice(order.getJewelries()),
                order.getDiscount(),
                order.getDeliveryCost())
        );
        return createResponseMessage(order);
    }

    private ResponseMessage calculateCostWithPromoCode(Order order, String promocode) {
        PromotionalCode promotionalCode = orderService.getPromotionalCode(promocode);
        if (promotionalCode != null && orderService.isValidPromoCode(promotionalCode)) {
            order.setPromocode(promotionalCode);
            double allJewelriesPrice = orderService.getAllJewelriesPrice(order.getJewelries());
            order.setDiscount(orderService.getDiscount(allJewelriesPrice, promotionalCode));
            order.setTotalCost(orderService.getTotalPrice(
                    allJewelriesPrice,
                    order.getDiscount(),
                    order.getDeliveryCost())
            );
        } else {
            order.setPromocode(null);
            order.setDiscount(0.0);
            order.setTotalCost(orderService.getTotalPrice(
                    orderService.getAllJewelriesPrice(order.getJewelries()),
                    order.getDiscount(),
                    order.getDeliveryCost())
            );
        }
        return createResponseMessage(order);
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
