package com.alena.preparationproject.mvc.controller.shop;

import com.alena.preparationproject.mvc.FormatHelper;
import com.alena.preparationproject.mvc.controller.base.ControllerHelper;
import com.alena.preparationproject.mvc.controller.base.ImageHelper;
import com.alena.preparationproject.mvc.model.Order;
import com.alena.preparationproject.mvc.model.PromotionalCode;
import com.alena.preparationproject.mvc.model.enums.PromoCodeType;
import com.alena.preparationproject.mvc.service.CreateOrderException;
import com.alena.preparationproject.mvc.service.OrderService;
import org.codehaus.jackson.map.ObjectMapper;
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
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries(HttpServletRequest request,
                                        @ModelAttribute("order") Order order) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/buy");
        modelAndView.addObject("order", order);
        modelAndView.addObject("imageHelper", new ImageHelper(ControllerHelper.getContextPath(request)));
        return modelAndView;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ModelAndView createOrder(@ModelAttribute("order") Order order) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            orderService.saveOrder(order);
            modelAndView.addObject("order", createOrder());
            modelAndView.addObject("type", InfoPageType.SUCCESSFUL_ORDER);
            modelAndView.setViewName("redirect:/info");
        } catch (CreateOrderException e) {
            switch (e.getExceptionType()) {
                case JEWELRY_SOLD:
                case JEWELRY_DOES_NOT_EXIST:
                    orderService.updateOrderAfterDeleteJewelry(order, e.getJewelry().getId());
                    break;
                case JEWELRY_PRICE_CHANGE:
                    order.getJewelries().remove(e.getJewelry());
                    orderService.updateOrderAfterAddJewelry(order, e.getJewelry().getId());
                    break;
                case PROMOCODE_DOES_NOT_EXIST:
                case PROMOCODE_IS_NOT_VALID:
                    orderService.updateOrderForInvalidPromoCode(order);
                    break;
                case PROMOCODE_IS_CHANGED:
                    orderService.updateOrderAfterAddPromoCode(order, order.getPromocode().getCode());
                    break;
                case DELIVERY_IS_CHANGED:
                    orderService.updateOrderAfterChangeDeliveryType(order, order.getDeliveryType().getId());
                    break;
            }
            modelAndView.setViewName("redirect:/buy");
            modelAndView.addObject("order", order);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public @ResponseBody
    String deleteItem(@ModelAttribute("order") Order order,
                      @RequestParam("itemId") Long jewelryId) throws IOException {
        orderService.updateOrderAfterDeleteJewelry(order, jewelryId);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(order));
    }

    @RequestMapping(value = "/checkPromoCode", method = RequestMethod.GET)
    public @ResponseBody
    String checkPromoCode(@ModelAttribute("order") Order order,
                          @RequestParam("code") String code) throws IOException {
        orderService.updateOrderAfterAddPromoCode(order, code);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(order));
    }

    @RequestMapping(value = "/checkDelivery", method = RequestMethod.GET)
    public @ResponseBody
    String checkDelivery(@ModelAttribute("order") Order order,
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
        PromotionalCode promocode = order.getPromocode();
        responseMessage.setValidPromocode(promocode != null);
        if (promocode != null && promocode.getPromoCodeType() == PromoCodeType.PERCENT
                && promocode.getMaxJewelries() != null && promocode.getMaxJewelries() != 0 &&
                order.getJewelries().size() > promocode.getMaxJewelries()) {
            responseMessage.setPromocodeLimit(promocode.getMaxJewelries().toString());
        }
        responseMessage.setFormatPromocode(FormatHelper.getPriceFormat(order.getDiscount()));
        responseMessage.setFormatDeliveryPrice(FormatHelper.getPriceFormat(order.getDeliveryCost()));
        responseMessage.setFormatTotalCost(FormatHelper.getPriceFormat(order.getTotalCost()));
        responseMessage.setFormatCostWithoutDiscount(FormatHelper.getPriceFormat(order.getCostWithoutDiscount()));
        return responseMessage;
    }
}
