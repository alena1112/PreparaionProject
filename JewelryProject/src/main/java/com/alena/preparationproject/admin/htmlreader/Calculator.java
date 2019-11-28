package com.alena.preparationproject.admin.htmlreader;

import com.alena.preparationproject.admin.model.JewelryItem;
import com.alena.preparationproject.admin.model.JewelryOrder;

public class Calculator {

    public Calculator() {
    }

    public void calculate(JewelryOrder order) {
        int numOfItems = 0;
        for (JewelryItem item : order.getItems()) {
            numOfItems += item.getNumber();
        }

        double delivery = order.getDeliveryAmount() / numOfItems;

        for (JewelryItem item : order.getItems()) {
            double price = item.getPrice() / item.getNumber() + delivery;
            item.setPriceWithDelivery(price);
        }
    }
}
