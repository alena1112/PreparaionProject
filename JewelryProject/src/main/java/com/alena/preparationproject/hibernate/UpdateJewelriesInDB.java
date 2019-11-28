package com.alena.preparationproject.hibernate;

import com.alena.preparationproject.mvc.model.*;
import com.alena.preparationproject.mvc.model.enums.JewelryType;
import com.alena.preparationproject.mvc.model.enums.PromoCodeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;

public class UpdateJewelriesInDB {

    public static void main(String[] args) {
        updateJewelries();
//        createPromocode();
    }

    private static Session getSession() {
        final SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        return sf.openSession();
    }

    private static Image createImage(String path, Session session) {
        Image image = new Image();
        image.setPath("http://localhost:9998/resources/w3images/" + path);
        session.save(image);
        return image;
    }

    private static void updateJewelries() {
        Session session = getSession();

        Transaction transaction = session.beginTransaction();

        session.createQuery("delete from Jewelry").executeUpdate();
        session.createQuery("delete from Image").executeUpdate();

        Jewelry jewelry1 = new Jewelry();
        jewelry1.setName("Graceful Shells");
        jewelry1.setPrice(0.0);
        jewelry1.setDescription("Яркие ассиметричные серьги с кристальным жемчугом Swarovski");
        jewelry1.setType(JewelryType.EARRINGS);
        jewelry1.setMainImage(createImage("1.jpg", session));
        session.save(jewelry1);

        Jewelry jewelry2 = new Jewelry();
        jewelry2.setName("Graceful Flowers");
        jewelry2.setPrice(0.0);
        jewelry2.setDescription("Нежные серьги со стеклянными бутонами лэмпворк");
        jewelry2.setType(JewelryType.EARRINGS);
        jewelry2.setMainImage(createImage("2.jpg", session));
        session.save(jewelry2);

        Jewelry jewelry3 = new Jewelry();
        jewelry3.setName("Graceful Metal");
        jewelry3.setPrice(0.0);
        jewelry3.setDescription("Позолоченные серьги мятый металл с эмалью");
        jewelry3.setType(JewelryType.EARRINGS);
        jewelry3.setMainImage(createImage("3.jpg", session));
        session.save(jewelry3);

        Jewelry jewelry4 = new Jewelry();
        jewelry4.setName("Graceful Pearls");
        jewelry4.setPrice(0.0);
        jewelry4.setDescription("Барочный жемчуг качества Grade AA, позолоченная фурнитура");
        jewelry4.setType(JewelryType.EARRINGS);
        jewelry4.setMainImage(createImage("4.jpg", session));
        session.save(jewelry4);

        Jewelry jewelry5 = new Jewelry();
        jewelry5.setName("Graceful Pink");
        jewelry5.setPrice(0.0);
        jewelry5.setDescription("Нежный браслет с миксом натурального пресноводного жемчуга и жемчуга бива, аметиста, розового опала");
        jewelry5.setType(JewelryType.BRACELET);
        jewelry5.setMainImage(createImage("5.jpg", session));
        session.save(jewelry5);

        Jewelry jewelry6 = new Jewelry();
        jewelry6.setName("Graceful Butterflies");
        jewelry6.setPrice(0.0);
        jewelry6.setDescription("Двойной браслет из натурального жемчуга с изящной застёжкой. Позолоченная фурнитура, инкрустация фианиты");
        jewelry6.setType(JewelryType.BRACELET);
        jewelry6.setMainImage(createImage("6.jpg", session));
        session.save(jewelry6);

        Jewelry jewelry7 = new Jewelry();
        jewelry7.setName("Graceful White");
        jewelry7.setPrice(0.0);
        jewelry7.setDescription("Роскошный браслет из натурального барочного жемчуга и удобной магнитной застежкой, инкрустация фианиты");
        jewelry7.setType(JewelryType.BRACELET);
        jewelry7.setMainImage(createImage("7.jpg", session));
        session.save(jewelry7);

        Jewelry jewelry8 = new Jewelry();
        jewelry8.setName("Graceful Aventurine");
        jewelry8.setPrice(2000.0);
        jewelry8.setDescription("Браслет с авантюриновым стеклом и лазуритом. Посеребрённая фурнитура, фианиты");
        jewelry8.setType(JewelryType.BRACELET);
        Image image = createImage("8.jpg", session);
        jewelry8.setMainImage(image);
        jewelry8.setImages(Arrays.asList(image));
        jewelry8.setMaterialDescription("Фурнитура с гипоаллегренным покрытием родий; натуральные камни лазурита; авантюриновое стекло; фианиты");
        jewelry8.setWeight("Длина браслета 10 см, длина цепочки 5 см");
        jewelry8.setSize("10 гр.");
        session.save(jewelry8);

        Jewelry jewelry9 = new Jewelry();
        jewelry9.setName("Graceful Quartz");
        jewelry9.setPrice(1600.0);
        jewelry9.setDescription("Нежный и невесомый браслет из бусин натурального говлита и кварца. Посеребрённая фурнитура со вставками из фианитов");
        jewelry9.setType(JewelryType.BRACELET);
        jewelry9.setMainImage(createImage("9.jpg", session));
        session.save(jewelry9);

        Jewelry jewelry10 = new Jewelry();
        jewelry10.setMainImage(createImage("10.jpg", session));
        session.save(jewelry10);

        Jewelry jewelry11 = new Jewelry();
        jewelry11.setMainImage(createImage("11.jpg", session));
        session.save(jewelry11);

        Jewelry jewelry12 = new Jewelry();
        jewelry12.setMainImage(createImage("12.jpg", session));
        session.save(jewelry12);

        transaction.commit();

        session.close();
    }

    private static void createPromocode() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        PromotionalCode promocode = new PromotionalCode();
        promocode.setCode("test");
        promocode.setActive(true);
        promocode.setPromoCodeType(PromoCodeType.PERCENT);
        promocode.setValue(20.0);

        session.save(promocode);

        transaction.commit();

        session.close();
    }
}
