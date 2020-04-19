package com.alena.jewelryproject.rich.htmlreader;

import com.alena.jewelryproject.model.Material;
import com.alena.jewelryproject.model.MaterialOrder;

public class Calculator {

    public Calculator() {
    }

    public void calculate(MaterialOrder order) {
        int numOfItems = 0;
        for (Material item : order.getMaterials()) {
            numOfItems += item.getNumber();
        }

        double delivery = order.getDeliveryPrice() / numOfItems;

        for (Material item : order.getMaterials()) {
            double price = item.getPrice() / item.getNumber() + delivery;
            item.setUnitPriceWithDelivery(price);
        }
    }
}
