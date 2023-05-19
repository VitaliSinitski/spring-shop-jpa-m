package com.vitali.util;

import lombok.experimental.UtilityClass;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class TestConstants {
    public static final Integer USER_ID_ONE = 1;
    public static final Integer USER_ID_TWO = 2;
    public static final Integer USER_INFORMATION_ID = 1;
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer THREE = 3;
    public static final Integer TEN = 10;
    public static final Integer CART_ID_ONE = 1;
    public static final Integer CART_ID_TWO = 2;
    public static final Integer ORDER_ID_ONE = 1;
    public static final Integer ORDER_ID_TWO = 2;
    public static final Integer CART_ITEM_ID_ONE = 1;
    public static final Integer CART_ITEM_ID_TWO = 2;
    public static final Integer ORDER_ITEM_ID_ONE = 1;
    public static final Integer ORDER_ITEM_ID_TWO = 2;
    public static final Integer TIMES_ONE = 1;
    public static final Integer SIZE_ZERO = 0;
    public static final Integer SIZE_ONE = 1;
    public static final Integer SIZE_TWO = 2;
    public static final Integer SIZE_FIVE = 5;
    public static final Integer QUANTITY_ZERO = 0;
    public static final Integer QUANTITY_ONE = 1;
    public static final Integer QUANTITY_TWO = 2;
    public static final Integer QUANTITY_THREE = 3;
    public static final Integer QUANTITY_FIVE = 5;
    public static final Integer QUANTITY_TEN = 10;
    public static final Integer PRODUCT_ID_ONE = 1;
    public static final Integer PRODUCT_ID_TWO = 2;
    public static final Integer PRODUCT_ID_THREE = 3;
    public static final Integer PRODUCT_QUANTITY_FIVE = 5;
    public static final Integer PRODUCER_ID = 1;
    public static final Integer CATEGORY_ID = 1;
    public static final BigDecimal PRODUCT_PRICE_TEN = BigDecimal.valueOf(10);
    public static final BigDecimal PRODUCT_PRICE_FIFTY = BigDecimal.valueOf(50);
    public static final BigDecimal PRODUCT_PRICE_ONE_HUNDRED = BigDecimal.valueOf(100);
    public static final BigDecimal PRODUCT_PRICE_ONE_HUNDRED_FIFTY = BigDecimal.valueOf(150);
    public static final String USER_NAME = "Jacky";
    public static final String USER_EMAIL = "jacky@gmail.com";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PASSWORD_NEW = "newPassword";
    public static final String USER_INFORMATION_FIRST_NAME = "Jacky";
    public static final String USER_INFORMATION_LAST_NAME = "Black";
    public static final String USER_INFORMATION_PHONE = "+1234567890";
    public static final String USER_INFORMATION_ADDRESS = "Test Address";
    public static final LocalDate USER_INFORMATION_BIRTH_DATE = LocalDate.of(2000, 1, 1);
    public static final String ORDER_INFORMATION = "Information";
    public static final String PRODUCT_NAME = "Test Product";
    public static final String PRODUCT_DESCRIPTION = "Test product description";
    public static final String CATEGORY_NAME = "New Category";
    public static final String PRODUCT_IMAGE_STRING = "image";
    public static final String PRODUCER_NAME = "New Producer";
    public static final String AUTHORITY = "authority";
    public static final Boolean ENABLED = true;
    public static final byte[] PRODUCT_IMAGE_BYTES = new byte[] { 0x12, 0x34, 0x56, 0x78 };
    public static final MockMultipartFile PRODUCT_IMAGE_FILE = new MockMultipartFile("image", "test.jpg", "image/jpeg", PRODUCT_IMAGE_BYTES);
    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2023,4,23,12,24,32);
}
