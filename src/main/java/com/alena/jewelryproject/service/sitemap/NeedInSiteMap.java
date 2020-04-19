package com.alena.jewelryproject.service.sitemap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedInSiteMap {
    String[] paths() default {};
    boolean isAllJewelryIds() default false;
}
