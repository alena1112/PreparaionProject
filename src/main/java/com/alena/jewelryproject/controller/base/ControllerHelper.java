package com.alena.jewelryproject.controller.base;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class ControllerHelper {
    //TODO it is not a good code
    public static String getContextPath(HttpServletRequest request) {
        if (StringUtils.isBlank(request.getContextPath())) {//for devmode
            return request.getRequestURL().toString().replace(request.getRequestURI(), "");
        } else {
            return request.getContextPath();
        }
    }
}
