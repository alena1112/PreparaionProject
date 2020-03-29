package com.alena.jewelryproject.mvc.service.sitemap;

import com.alena.jewelryproject.mvc.service.JewelryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SiteMapService {
    private static final Logger log = LoggerFactory.getLogger(SiteMapService.class);

    @Value("${base.path}")
    private String basePath;

    @Value("${app.path}")
    private String appPath;

    @Autowired
    private JewelryService jewelryService;

    private Date changeDate = new Date();
    private static final String PACKAGE_NAME = "com.alena.jewelryproject.mvc.controller.shop";

    public Urlset generateUrlset() {
        List<Url> urls = new ArrayList<>();
        urls.add(new Url(basePath, changeDate, 1.00));
        try {
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));

            scanner.findCandidateComponents(PACKAGE_NAME).forEach(beanDefinition -> {

            });
            for (BeanDefinition beanDefinition : scanner.findCandidateComponents(PACKAGE_NAME)) {
                Class<?> controller = Class.forName(beanDefinition.getBeanClassName());
                RequestMapping requestMapping = controller.getAnnotation(RequestMapping.class);

                String controllerPath = basePath + appPath;
                if (requestMapping != null) {
                    controllerPath += requestMapping.value()[0];
                }

                for (Method method : controller.getMethods()) {
                    RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                    NeedInSiteMap methodNeedInSiteMap = method.getAnnotation(NeedInSiteMap.class);
                    if (methodRequestMapping != null &&
                            (!isMethodContainsRequestParam(method) || methodNeedInSiteMap != null)) {
                        String[] value = methodRequestMapping.value();
                        if (value.length == 0) {
                            urls.addAll(getUrlsForMethodWithSpecialAnnotation(methodNeedInSiteMap, controllerPath));
                        } else {
                            urls.addAll(getUrlsForMethodWithSpecialAnnotation(methodNeedInSiteMap,
                                    controllerPath + methodRequestMapping.value()[0]));
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("Error while generating site map", e);
        }
        return new Urlset(urls);
    }

    private boolean isMethodContainsRequestParam(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] annotations : parameterAnnotations) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequestParam ||
                        annotation instanceof ModelAttribute) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Url> getUrlsForMethodWithSpecialAnnotation(NeedInSiteMap annotation, String controllerPath) {
        List<Url> urls = new ArrayList<>();
        if (annotation != null) {
            if (annotation.paths().length != 0) {
                for (String path : annotation.paths()) {
                    urls.add(new Url(controllerPath + path,
                            changeDate, 0.80));
                }
            } else if (annotation.isAllJewelryIds()) {
                jewelryService.getAllUnhiddenJewelries()
                        .forEach(jewelry ->
                                urls.add(new Url(controllerPath + "?id=" + jewelry.getId(), changeDate, 0.80))
                        );

            }
        } else {
            urls.add(new Url(controllerPath, changeDate, 0.80));
        }
        return urls;
    }
}
