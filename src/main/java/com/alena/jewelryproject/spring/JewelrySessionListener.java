package com.alena.jewelryproject.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

public class JewelrySessionListener extends HttpSessionEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(JewelrySessionListener.class);

    private static final int SESSION_TIME_SEC = 60 * 60;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        super.sessionCreated(event);
        log.info(String.format("Create new user session: id %s", event.getSession().getId()));
        //Установка таймаута сессии
        event.getSession().setMaxInactiveInterval(SESSION_TIME_SEC);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        super.sessionDestroyed(event);
    }

    private Object getBean(HttpSessionEvent event, String name) {
        HttpSession session = event.getSession();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        return ctx.getBean(name);
    }
}
