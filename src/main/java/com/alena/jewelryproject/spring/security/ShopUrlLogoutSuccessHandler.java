package com.alena.jewelryproject.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ShopUrlLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(ShopUrlLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Logout was doing successfully");
        super.onLogoutSuccess(request, response, authentication);
    }
}
