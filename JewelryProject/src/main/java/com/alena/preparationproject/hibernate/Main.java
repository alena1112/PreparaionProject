package com.alena.preparationproject.hibernate;

import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.Material;
import com.alena.preparationproject.web.model.Shop;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        final SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        final Session session = sf.openSession();

        Transaction transaction = session.beginTransaction();

        Shop shop = new Shop();
        shop.setName("test");
        session.save(shop);

        Jewelry jewelry1 = new Jewelry();
        jewelry1.setName("jewelry 1");
        jewelry1.setPrice(100.0);

        Jewelry jewelry2 = new Jewelry();
        jewelry2.setName("jewelry 2");
        jewelry2.setPrice(200.0);

        session.save(jewelry1);
        session.save(jewelry2);

        System.out.println("Shops");
        List<Shop> shops = session.createQuery("select s from Shop s", Shop.class).getResultList();
        shops.forEach(s -> System.out.println(s.getId() + " " + s.getName()));

        System.out.println("Materials");
        List<Material> materials = session.createQuery("select m from Material m", Material.class).getResultList();
        materials.forEach(m -> System.out.println(m.getId() + " " + m.getName() + " " + m.getPrice() + " " + m.getShop().getName()));

        System.out.println("Jewelries");
        List<Jewelry> jewelries = session.createQuery("select j from Jewelry j", Jewelry.class).getResultList();
        jewelries.forEach(j -> System.out.println(j.getId() + " " + j.getName() + " " + j.getDescription() + " " + j.getPrice() + " "));

        transaction.commit();

        session.close();

    }
}
