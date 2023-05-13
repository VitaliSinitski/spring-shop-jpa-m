package com.vitali.constants;

import com.vitali.database.entities.enums.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public final static String NAME = "name";
    public final static String USERNAME = "username";
    public final static String FIRST_NAME = "firstName";
    public final static String LAST_NAME = "lastName";
    public final static String BIRTH_DATE = "birthDate";
    public final static String USER = "user";
    public final static String ADDRESS = "address";
    public final static String PHONE = "phone";
    public final static String PASSWORD = "password";
    public final static String ROLE = "role";
    public final static String CART_ID = "cartId";
    public final static String PRODUCT_ID = "productId";
    public static final String QUANTITY = "quantity";
    public static final String EMAIL = "email";
    public static final Role DEFAULT_ROLE = Role.USER;
    public static final Integer DEFAULT_QUANTITY = 1;
    public static final boolean DEFAULT_ENABLED = true;
    public static final String IMAGE_BASE_PATH = "/image";

}
