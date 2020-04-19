package com.alena.jewelryproject.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/about").setViewName("shop/info/about");
        registry.addViewController("/delivery").setViewName("shop/info/delivery");
        registry.addViewController("/payment").setViewName("shop/info/payment");
        registry.addViewController("/return").setViewName("shop/info/return");
        registry.addViewController("/contacts").setViewName("shop/info/contacts");
    }
}
