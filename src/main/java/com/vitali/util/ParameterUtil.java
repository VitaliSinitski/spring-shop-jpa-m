package com.vitali.util;

import com.vitali.database.entities.enums.Role;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.vitali.constants.Constants.*;

@Slf4j
public class ParameterUtil {

    public static Integer getQuantity(HttpServletRequest request) {
        return Optional.ofNullable(Integer.parseInt(request.getParameter(QUANTITY))).orElse(DEFAULT_QUANTITY);
    }


    public static Integer getIntegerFromRequest(String name, HttpServletRequest request) {
        return Optional.of(Integer.parseInt(request.getParameter(name))).orElse(null);
    }

    public static Integer getIntegerFromObject(Object object) {
        return Optional.ofNullable((Integer) object).orElse(null);
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
