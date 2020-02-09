package com.alena.jewelryproject.mvc.controller.shop;

import com.alena.jewelryproject.mvc.FormatHelper;
import com.alena.jewelryproject.mvc.controller.base.ControllerHelper;
import com.alena.jewelryproject.mvc.controller.base.ImageHelper;
import com.alena.jewelryproject.mvc.model.Order;
import com.alena.jewelryproject.mvc.model.PromotionalCode;
import com.alena.jewelryproject.mvc.model.enums.DeliveryType;
import com.alena.jewelryproject.mvc.model.enums.PromoCodeType;
import com.alena.jewelryproject.mvc.service.CreateOrderException;
import com.alena.jewelryproject.mvc.service.OrderService;
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
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries(HttpServletRequest request) {
        Order order = getOrderFromSession(request);
        //TODO сделано из-за невозможности заинжектить в листенер сервис!
        if (order.getDeliveryType() != DeliveryType.PICKUP && order.getDeliveryCost() == null) {
            orderService.updateOrderAfterChangeDeliveryType(order, order.getDeliveryType().getId());
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/buy");
        modelAndView.addObject("order", order);
        modelAndView.addObject("imageHelper", new ImageHelper(ControllerHelper.getContextPath(request)));
        return modelAndView;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ModelAndView createOrder(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Order order = getOrderFromSession(request);
        try {
            orderService.saveOrder(order);
            modelAndView.addObject("order", order);
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
    String deleteItem(HttpServletRequest request,
                      @RequestParam("itemId") Long jewelryId) throws IOException {
        Order order = getOrderFromSession(request);
        orderService.updateOrderAfterDeleteJewelry(order, jewelryId);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(order));
    }

    @RequestMapping(value = "/checkPromoCode", method = RequestMethod.GET)
    public @ResponseBody
    String checkPromoCode(HttpServletRequest request,
                          @RequestParam("code") String code) throws IOException {
        Order order = getOrderFromSession(request);
        orderService.updateOrderAfterAddPromoCode(order, code);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(order));
    }

    @RequestMapping(value = "/checkDelivery", method = RequestMethod.GET)
    public @ResponseBody
    String checkDelivery(HttpServletRequest request,
                         @RequestParam("type") String type) throws IOException {
        Order order = getOrderFromSession(request);
        orderService.updateOrderAfterChangeDeliveryType(order, type);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createResponseMessage(order));
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
