package com.alena.jewelryproject.mvc.controller.shop;

import com.alena.jewelryproject.mvc.FormatHelper;
import com.alena.jewelryproject.mvc.controller.base.ControllerHelper;
import com.alena.jewelryproject.mvc.controller.base.ImageHelper;
import com.alena.jewelryproject.mvc.model.Order;
import com.alena.jewelryproject.mvc.model.PromotionalCode;
import com.alena.jewelryproject.mvc.model.enums.PromoCodeType;
import com.alena.jewelryproject.mvc.service.CreateOrderException;
import com.alena.jewelryproject.mvc.service.OrderService;
import com.alena.jewelryproject.spring.ShoppingCart;
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
    @Autowired
    private ShoppingCart shoppingCart;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/buy");
        modelAndView.addObject("order", shoppingCart.getOrder());
        modelAndView.addObject("imageHelper", new ImageHelper(ControllerHelper.getContextPath(request)));
        return modelAndView;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ModelAndView createOrder(HttpServletRequest request,
                                    @ModelAttribute("order") Order order) {
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
                    orderService.updateOrderAfterChangeDeliveryType(shoppingCart.getOrder(), shoppingCart.getOrder().getDeliveryType().getId());
                    break;
            }
            modelAndView.setViewName("redirect:/buy");
        } finally {
            modelAndView.addObject("order", shoppingCart.getOrder());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public @ResponseBody
    String deleteItem(HttpServletRequest request,
                      @RequestParam("itemId") Long jewelryId) throws IOException {
        orderService.updateOrderAfterDeleteJewelry(shoppingCart.getOrder(), jewelryId);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(shoppingCart.getOrder()));
    }

    @RequestMapping(value = "/checkPromoCode", method = RequestMethod.GET)
    public @ResponseBody
    String checkPromoCode(HttpServletRequest request,
                          @RequestParam("code") String code) throws IOException {
        orderService.updateOrderAfterAddPromoCode(shoppingCart.getOrder(), code);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(shoppingCart.getOrder()));
    }

    @RequestMapping(value = "/checkDelivery", method = RequestMethod.GET)
    public @ResponseBody
    String checkDelivery(HttpServletRequest request,
                         @RequestParam("type") String type) throws IOException {
        orderService.updateOrderAfterChangeDeliveryType(shoppingCart.getOrder(), type);
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
        responseMessage.setFormatDeliveryPrice(FormatHelper.getPriceFormat(order.getDeliveryCost()));
        responseMessage.setFormatTotalCost(FormatHelper.getPriceFormat(order.getTotalCost()));
        responseMessage.setFormatCostWithoutDiscount(FormatHelper.getPriceFormat(order.getCostWithoutDiscount()));
        return responseMessage;
    }
}
