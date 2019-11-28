package com.alena.preparationproject.admin;

import com.bijou.web.model.JewelryItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

public class JewelryItemCreateForm extends FormLayout {

    private MainWeb mainWeb;

//    private TextField name = new TextField("JewelryName");
    private TextField imageURL = new TextField("imageURL");
    private TextField number = new TextField("number");
    private TextField price = new TextField("price");
    private TextField delivery = new TextField("delivery");
//    private ComboBox<JewelryOrder> orders = new ComboBox<>("orders");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<JewelryItem> binder = new Binder<>(JewelryItem.class);

    public JewelryItemCreateForm(MainWeb mainWeb) {
        this.mainWeb = mainWeb;

        binder.forField(number)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(0, "integers only"))
                .bind(JewelryItem::getNumber, JewelryItem::setNumber);
        binder.forField(price)
                .withNullRepresentation("")
                .withConverter(new StringToDoubleConverter(0.0, "integers only"))
                .bind(JewelryItem::getPrice, JewelryItem::setPrice);
        binder.forField(delivery)
                .withNullRepresentation("")
                .withConverter(new StringToDoubleConverter(0.0, "integers only"))
                .bind(JewelryItem::getPriceWithDelivery, JewelryItem::setPriceWithDelivery);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(/*name, */imageURL, number, price, delivery, buttons);

        binder.bindInstanceFields(this);

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
    }

    public void setJewelryItem(JewelryItem jewelryItem) {
        binder.setBean(jewelryItem);

        if (jewelryItem == null) {
            setVisible(false);
        } else {
            setVisible(true);
//            name.focus();
        }
    }

    private void save() {
        JewelryItem jewelryItem = binder.getBean();
        //TODO
//        service.save(jewelryItem);
//        mainWeb.updateList();
        setJewelryItem(null);
    }

    private void delete() {
        JewelryItem jewelryItem = binder.getBean();
//        service.delete(jewelryItem);
//        mainView.updateList();
        setJewelryItem(null);
    }
}
