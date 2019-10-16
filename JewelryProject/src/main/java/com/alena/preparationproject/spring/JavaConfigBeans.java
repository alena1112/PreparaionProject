package com.alena.preparationproject.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class JavaConfigBeans {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("PersistenceUnit");//META-INF/persistence.xml
    }

//    @Bean
//    public JpaTransactionManager transactionManager() {
//        return new JpaTransactionManager(entityManagerFactory());
//    }
}
