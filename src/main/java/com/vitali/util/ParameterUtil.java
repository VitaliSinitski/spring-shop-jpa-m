package com.vitali.util;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.vitali.constants.Constants.DEFAULT_QUANTITY;
import static com.vitali.constants.Constants.QUANTITY;

public class ParameterUtil {

    public static Integer getQuantity(HttpServletRequest request) {
        return Optional.of(Integer.parseInt(request.getParameter(QUANTITY))).orElse(DEFAULT_QUANTITY);
    }

    public static Integer getIntegerFromRequest(String name, HttpServletRequest request) {
        return Optional.of(Integer.parseInt(request.getParameter(name))).orElse(null);
    }

    public static Long getLongFromRequest(String name, HttpServletRequest request) {
        return Optional.of(Long.getLong(request.getParameter(name))).orElse(null);
    }
}
