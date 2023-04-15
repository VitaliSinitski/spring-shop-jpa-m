package com.vitali.util;

import com.vitali.entities.enums.Role;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.vitali.constants.Constants.*;

public class ParameterUtil {

    public static Integer getQuantity(HttpServletRequest request) {
        return Optional.ofNullable(Integer.parseInt(request.getParameter(QUANTITY))).orElse(DEFAULT_QUANTITY);
    }


    public static Integer getIntegerFromRequest(String name, HttpServletRequest request) {
        return Optional.of(Integer.parseInt(request.getParameter(name))).orElse(null);
    }

    public static Long getLongFromRequest(String name, HttpServletRequest request) {
        return Optional.ofNullable(Long.getLong(request.getParameter(name))).orElse(null);
    }

    public static Role getRole(HttpServletRequest request) {
        if (request.getParameter(ROLE) == null || request.getParameter(ROLE).isEmpty()) {
            return DEFAULT_ROLE;
        } else {
            return Role.valueOf(request.getParameter(ROLE));
        }
    }

    public static boolean getEnabled(HttpServletRequest request) {
            return DEFAULT_ENABLED;
    }

}
