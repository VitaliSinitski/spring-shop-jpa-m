package com.vitali.util;

import com.vitali.database.entities.enums.Role;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
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
        return Optional.ofNullable((Integer)object).orElse(null);
    }

    public static List<Integer> getIntegerListFromObject(String[] selectedItems) {
        List<Integer> result = new ArrayList<>();
        if (selectedItems != null) {
            for (String item : selectedItems) {
                try {
                    result.add(Integer.parseInt(item));
                } catch (NumberFormatException e) {
                    log.warn("Ignoring non-integer value: {}", item);
                }
            }
        }
        return result;
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
