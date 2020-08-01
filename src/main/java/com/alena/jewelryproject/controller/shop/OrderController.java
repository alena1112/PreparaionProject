package com.alena.jewelryproject.controller.shop;

import com.alena.jewelryproject.FormatHelper;
import com.alena.jewelryproject.controller.base.ControllerHelper;
import com.alena.jewelryproject.controller.base.ImageHelper;
import com.alena.jewelryproject.model.Order;
import com.alena.jewelryproject.model.PromotionalCode;
import com.alena.jewelryproject.model.enums.PromoCodeType;
import com.alena.jewelryproject.service.CreateOrderException;
import com.alena.jewelryproject.service.OrderService;
import com.alena.jewelryproject.spring.ShoppingCart;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@SessionAttributes(value = "order")
@RequestMapping("/buy")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShoppingCart shoppingCart;

    @GetMapping
    public ModelAndView getAllJewelries(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/buy");
        modelAndView.addObject("order", shoppingCart.getOrder());
        modelAndView.addObject("imageHelper", new ImageHelper(ControllerHelper.getContextPath(request)));
        return modelAndView;
    }

    @PostMapping("/createOrder")
    public ModelAndView createOrder(HttpServletRequest request,
                                    @ModelAttribute("order") Order order) {
        log.info("Getting order attribute: " + order.toString());
        ModelAndView modelAndView = new ModelAndView();
        try {
            orderService.saveOrder(order);
            shoppingCart.setOrder(null);

            modelAndView.addObject("type", InfoPageType.SUCCESSFUL_ORDER);
            modelAndView.setViewName("redirect:/info");
        } catch (CreateOrderException e) {
            switch (e.getExceptionType()) {
                case JEWELRY_SOLD:
                case JEWELRY_DOES_NOT_EXIST:
                    orderService.updateOrderAfterDeleteJewelry(shoppingCart.getOrder(), e.getJewelry().getId());
                    break;
                case JEWELRY_PRICE_CHANGE:
                    shoppingCart.getOrder().getJewelries().remove(e.getJewelry());
                    orderService.updateOrderAfterAddJewelry(shoppingCart.getOrder(), e.getJewelry().getId());
                    break;
                case PROMOCODE_DOES_NOT_EXIST:
                case PROMOCODE_IS_NOT_VALID:
                    orderService.updateOrderForInvalidPromoCode(shoppingCart.getOrder());
                    break;
                case PROMOCODE_IS_CHANGED:
                    orderService.updateOrderAfterAddPromoCode(shoppingCart.getOrder(), shoppingCart.getOrder().getPromocode().getCode());
                    break;
                case DELIVERY_IS_CHANGED:
                    orderService.updateOrderAfterChangeDeliveryType(shoppingCart.getOrder(),
                            shoppingCart.getOrder().getDeliveryType().getId(),
                            shoppingCart.getOrder().getUserData().getCountry());
                    break;
            }
            modelAndView.setViewName("redirect:/buy");
        } finally {
            modelAndView.addObject("order", shoppingCart.getOrder());
        }
        return modelAndView;
    }

    @GetMapping("/deleteItem")
    public @ResponseBody
    String deleteItem(HttpServletRequest request,
                      @RequestParam("itemId") Long jewelryId) throws IOException {
        orderService.updateOrderAfterDeleteJewelry(shoppingCart.getOrder(), jewelryId);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(shoppingCart.getOrder()));
    }

    @GetMapping("/checkPromoCode")
    public @ResponseBody
    String checkPromoCode(HttpServletRequest request,
                          @RequestParam("code") String code) throws IOException {
        orderService.updateOrderAfterAddPromoCode(shoppingCart.getOrder(), code);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(shoppingCart.getOrder()));
    }

    @GetMapping("/checkDelivery")
    public @ResponseBody
    String checkDelivery(HttpServletRequest request,
                         @RequestParam("type") String type,
                         @RequestParam("country") String country) throws IOException {
        orderService.updateOrderAfterChangeDeliveryType(shoppingCart.getOrder(), type, country);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(shoppingCart.getOrder()));
    }

    private ResponseMessage createResponseMessage(Order order) {
        ResponseMessage responseMessage = new ResponseMessage();
        PromotionalCode promocode = order.getPromocode();
        responseMessage.setValidPromocode(promocode != null);
        if (promocode != null && promocode.getPromoCodeType() == PromoCodeType.PERCENT
                && promocode.getMaxJewelries() != null && promocode.getMaxJewelries() != 0 &&
                order.getJewelries().size() > promocode.getMaxJewelries()) {
            responseMessage.setPromocodeLimit(promocode.getMaxJewelries().toString());
        }
        responseMessage.setFormatPromocode(FormatHelper.getPriceFormat(order.getDiscount()));
        responseMessage.setFormatDeliveryPrice(order.getDeliveryCost() != null ?
                FormatHelper.getPriceFormat(order.getDeliveryCost()) : null);
        responseMessage.setFormatTotalCost(FormatHelper.getPriceFormat(order.getTotalCost()));
        responseMessage.setFormatCostWithoutDiscount(FormatHelper.getPriceFormat(order.getCostWithoutDiscount()));
        return responseMessage;
    }
}
