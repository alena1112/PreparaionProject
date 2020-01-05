package com.alena.preparationproject.hibernate;

import com.alena.preparationproject.admin.htmlreader.*;
import com.alena.preparationproject.mvc.model.*;
import com.alena.preparationproject.mvc.model.enums.JewelryType;
import com.alena.preparationproject.mvc.model.enums.PromoCodeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateJewelriesInDB {
    private static final Logger log = LoggerFactory.getLogger(UpdateJewelriesInDB.class);

    public static void main(String[] args) {
        updateJewelries();
//        createPromocode();
//        createMaterials();
        createSettings();
    }

    private static Session getSession() {
        final SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        return sf.openSession();
    }

    private static Image createImage(String name, Jewelry jewelry, boolean isMainImage, Session session) {
        Image image = new Image();
        image.setName(name);
        image.setJewelry(jewelry);
        image.setIndex(0);
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
        session.save(jewelry1);
        jewelry1.setImages(new ArrayList<>());
//        jewelry1.getImages().add(createImage("1.jpg", jewelry1, true, session));

        Jewelry jewelry2 = new Jewelry();
        jewelry2.setName("Graceful Flowers");
        jewelry2.setPrice(0.0);
        jewelry2.setDescription("Нежные серьги со стеклянными бутонами лэмпворк");
        jewelry2.setType(JewelryType.EARRINGS);
        session.save(jewelry2);
        jewelry2.setImages(new ArrayList<>());
//        jewelry2.getImages().add(createImage("2.jpg", jewelry2, true, session));

        Jewelry jewelry3 = new Jewelry();
        jewelry3.setName("Graceful Metal");
        jewelry3.setPrice(0.0);
        jewelry3.setDescription("Позолоченные серьги мятый металл с эмалью");
        jewelry3.setType(JewelryType.EARRINGS);
        session.save(jewelry3);
        jewelry3.setImages(new ArrayList<>());
//        jewelry3.getImages().add(createImage("3.jpg", jewelry3, true, session));

        Jewelry jewelry4 = new Jewelry();
        jewelry4.setName("Graceful Pearls");
        jewelry4.setPrice(0.0);
        jewelry4.setDescription("Барочный жемчуг качества Grade AA, позолоченная фурнитура");
        jewelry4.setType(JewelryType.EARRINGS);
        session.save(jewelry4);
        jewelry4.setImages(new ArrayList<>());
//        jewelry4.getImages().add(createImage("4.jpg", jewelry4, true, session));

        Jewelry jewelry5 = new Jewelry();
        jewelry5.setName("Graceful Pink");
        jewelry5.setPrice(0.0);
        jewelry5.setDescription("Нежный браслет с миксом натурального пресноводного жемчуга и жемчуга бива, аметиста, розового опала");
        jewelry5.setType(JewelryType.BRACELET);
        session.save(jewelry5);
        jewelry5.setImages(new ArrayList<>());
//        jewelry5.getImages().add(createImage("5.jpg", jewelry5, true, session));

        Jewelry jewelry6 = new Jewelry();
        jewelry6.setName("Graceful Butterflies");
        jewelry6.setPrice(0.0);
        jewelry6.setDescription("Двойной браслет из натурального жемчуга с изящной застёжкой. Позолоченная фурнитура, инкрустация фианиты");
        jewelry6.setType(JewelryType.BRACELET);
        session.save(jewelry6);
        jewelry6.setImages(new ArrayList<>());
//        jewelry6.getImages().add(createImage("6.jpg", jewelry6, true, session));

        Jewelry jewelry7 = new Jewelry();
        jewelry7.setName("Graceful White");
        jewelry7.setPrice(0.0);
        jewelry7.setDescription("Роскошный браслет из натурального барочного жемчуга и удобной магнитной застежкой, инкрустация фианиты");
        jewelry7.setType(JewelryType.BRACELET);
        session.save(jewelry7);
        jewelry7.setImages(new ArrayList<>());
//        jewelry7.getImages().add(createImage("7.jpg", jewelry7, true, session));

        Jewelry jewelry8 = new Jewelry();
        jewelry8.setName("Graceful Aventurine");
        jewelry8.setPrice(2000.0);
        jewelry8.setDescription("Браслет с авантюриновым стеклом и лазуритом. Посеребрённая фурнитура, фианиты");
        jewelry8.setType(JewelryType.BRACELET);
        jewelry8.setMaterialDescription("Фурнитура с гипоаллегренным покрытием родий; натуральные камни лазурита; авантюриновое стекло; фианиты");
        jewelry8.setWeight("Длина браслета 10 см, длина цепочки 5 см");
        jewelry8.setSize("10 гр.");
        session.save(jewelry8);
        jewelry8.setImages(new ArrayList<>());
//        jewelry8.getImages().add(createImage("8.jpg", jewelry8, true, session));

        Jewelry jewelry9 = new Jewelry();
        jewelry9.setName("Graceful Quartz");
        jewelry9.setPrice(1600.0);
        jewelry9.setDescription("Нежный и невесомый браслет из бусин натурального говлита и кварца. Посеребрённая фурнитура со вставками из фианитов");
        jewelry9.setType(JewelryType.BRACELET);
        session.save(jewelry9);
        jewelry9.setImages(new ArrayList<>());
//        jewelry9.getImages().add(createImage("9.jpg", jewelry9, true, session));

        Jewelry jewelry10 = new Jewelry();
        session.save(jewelry10);
        jewelry10.setImages(new ArrayList<>());
//        jewelry10.getImages().add(createImage("10.jpg", jewelry10, true, session));

        Jewelry jewelry11 = new Jewelry();
        session.save(jewelry11);
        jewelry11.setImages(new ArrayList<>());
//        jewelry11.getImages().add(createImage("11.jpg", jewelry11, true, session));

        Jewelry jewelry12 = new Jewelry();
        session.save(jewelry12);
        jewelry12.setImages(new ArrayList<>());
//        jewelry12.getImages().add(createImage("12.jpg", jewelry12, true, session));

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

    private static void createSettings() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("delete from Settings").executeUpdate();

        Settings settings = new Settings();
        settings.setKey("delivery.cost.russiaPostOffice");
        settings.setValue("200.0");

        session.save(settings);

        transaction.commit();
        session.close();
    }

    private static void createMaterials() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();

            session.createQuery("delete from Material").executeUpdate();
            session.createQuery("delete from MaterialOrder").executeUpdate();
            session.createQuery("delete from Shop").executeUpdate();

            ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
            Calculator calculator = (Calculator) context.getBean("calculator");

            File dirFile = new File("/Users/alena/Desktop/Мои проги/PreparationProject/JewelryProject/src/main/java/com/alena/preparationproject/admin/html");
//            File dirFile = new File("C:/Users/KovalenkoAI/Desktop/Личное/PreparaionProject/JewelryProject/src/main/java/com/alena/preparationproject/admin/html");

            List<Shop> shops = new ArrayList<>();

            for (File dir : dirFile.listFiles()) {
                log.info(String.format("Starting parse directory %s", dir.getName()));
                Shops shopId = Shops.getShopById(dir.getName());
                if (shopId != null) {

                    Shop shop = shops.stream()
                            .filter(item -> item.getName().equals(shopId.name()))
                            .findAny()
                            .orElse(null);
                    if (shop == null) {
                        shop = new Shop();
                        shop.setName(shopId.name());
                        shops.add(shop);
                        session.save(shop);
                        log.info(String.format("Shop %s is created successfully", shop.getName()));
                    }


                    HtmlReader htmlReader = (HtmlReader) context.getBean(shopId.getId() + "Parser");
                    MaterialOrder order;
                    for (File file : dir.listFiles()) {
                        order = htmlReader.parse(file, LineParser.parseLine(shopId), DeliveryCostParser.parseDelivery(shopId),
                                shop);
                        calculator.calculate(order);
                        if (order != null) {
                            session.save(order);
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error("Error while update database", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (transaction != null) {
                transaction.commit();
            }
            if (session != null) {
                session.close();
            }
        }
    }
}
