package com.alena.jewelryproject.spring;

import com.alena.jewelryproject.mvc.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

public class JewelrySessionListener extends HttpSessionEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(JewelrySessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        super.sessionCreated(event);
        String ip = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();
        log.info(String.format("Create new user session: ip %s", ip));
        //Установка таймаута сессии
        event.getSession().setMaxInactiveInterval(60 * 10);

        event.getSession().setAttribute("order", OrderService.createDefaultOrder());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
//        String name = null;
//        SessionRegistry sessionRegistry = (SessionRegistry) getBean(event, "sessionRegistry");
//        SessionInformation sessionInfo = (sessionRegistry != null ? sessionRegistry
//                .getSessionInformation(event.getSession().getId()) : null);
//        UserDetails ud = null;
//        if (sessionInfo != null) ud = (UserDetails) sessionInfo.getPrincipal();
//        if (ud != null) {
//            name = ud.getUsername();
//        }
        super.sessionDestroyed(event);
    }

    private Object getBean(HttpSessionEvent event, String name) {
        HttpSession session = event.getSession();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        return ctx.getBean(name);
    }
}
