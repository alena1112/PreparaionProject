package com.alena.jewelryproject.spring;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@PropertySource({
        "classpath:application.properties",
        "classpath:application-${spring.profiles.active}.properties"
})
public class JavaConfigBeans {

    //бин для обработки файла свойств application.properties
    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Value("${hibernate.hbm2ddl.auto}") String update,
            @Value("${hibernate.connection.autocommit}") String autocommit,
            @Value("${hibernate.show_sql}") String showSql,
            @Value("${hibernate.format_sql}") String formatSql,
            @Value("${hibernate.dialect}") String dialect,
            @Value("${db.driver}") String driver,
            @Value("${db.url}") String url,
            @Value("${db.user}") String user,
            @Value("${db.password}") String password) {

        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", update);
        properties.put("hibernate.connection.autocommit", autocommit);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.format_sql", formatSql);

        properties.put("hibernate.dialect", dialect);
        properties.put("javax.persistence.jdbc.driver", driver);
        properties.put("javax.persistence.jdbc.url", url);
        properties.put("javax.persistence.jdbc.user", user);
        properties.put("javax.persistence.jdbc.password", password);

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactory.setPackagesToScan("com.alena.jewelryproject.model");
        entityManagerFactory.setPersistenceUnitName("PersistenceUnit");
        entityManagerFactory.setJpaPropertyMap(properties);
        entityManagerFactory.setLoadTimeWeaver(new ReflectiveLoadTimeWeaver());
        return entityManagerFactory;
    }

//    @Bean
//    public InternalResourceViewResolver resolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/pages/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }

    @Bean
    public JavaMailSender mailSender(
            @Value("${mail.smtp.host}") String host,
            @Value("${mail.smtp.port}") Integer port,
            @Value("${mail.username}") String username,
            @Value("${mail.password}") String password,
            @Value("${mail.smtp.auth}") boolean smtpAuth,
            @Value("${mail.smtp.socketFactory.class}") String clazz,
            @Value("${mail.smtp.socketFactory.port}") Integer sslPort,
            @Value("${mail.debug}") boolean mailDebug) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.socketFactory.class", clazz);
        properties.put("mail.smtp.socketFactory.port", sslPort);
        properties.put("mail.debug", mailDebug);

        return mailSender;
    }
}
