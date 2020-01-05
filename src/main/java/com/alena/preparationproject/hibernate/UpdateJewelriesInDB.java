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
//        updateJewelries();
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

        Settings settings2 = new Settings();
        settings2.setKey("maxNewJewelryCount");
        settings2.setValue("10");
        session.save(settings2);

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
