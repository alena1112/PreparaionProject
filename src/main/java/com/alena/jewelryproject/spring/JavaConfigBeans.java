package com.alena.jewelryproject.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

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
    public InternalResourceViewResolver resolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

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
