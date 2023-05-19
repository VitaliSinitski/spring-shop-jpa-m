package com.vitali.util;

import com.vitali.database.entities.*;
import com.vitali.database.entities.enums.Role;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.dto.category.CategoryReadDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.dto.producer.ProducerReadDto;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductFilter;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.user.UserReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

import static com.vitali.util.TestConstants.*;

@UtilityClass
public class MockUtils {
    public static final Cart CART = Cart.builder().id(CART_ID_ONE).build();
    public static final CartItemReadDto CART_ITEM_READ_DTO = new CartItemReadDto();
    public static final CartItemCreateDto CART_ITEM_CREATE_DTO = CartItemCreateDto.builder().quantity(QUANTITY_TWO)
            .productId(PRODUCT_ID_ONE).cartId(CART_ID_ONE).build();
    public static final Product PRODUCT_ONE = Product.builder().id(PRODUCT_ID_ONE).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION)
            .price(PRODUCT_PRICE_TEN).build();
    public static final Product PRODUCT_ONE_WITH_IMAGE_STRING = Product.builder().id(PRODUCT_ID_ONE).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION)
            .image(PRODUCT_IMAGE_STRING).price(PRODUCT_PRICE_TEN).build();
    public static final Product PRODUCT_TWO = Product.builder().id(PRODUCT_ID_TWO).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION).build();
    public static final Product PRODUCT = Product.builder().price(PRODUCT_PRICE_TEN).build();
    public static final Product MAPPED_PRODUCT = Product.builder().price(PRODUCT_PRICE_TEN).build();
    public static final Product UPDATED_PRODUCT_ONE = Product.builder().id(PRODUCT_ID_ONE).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION).price(PRODUCT_PRICE_TEN).build();
    public static final ProductReadDto PRODUCT_READ_DTO = ProductReadDto.builder().price(PRODUCT_PRICE_TEN).build();
    public static final ProductReadDto PRODUCT_READ_DTO_ONE = ProductReadDto.builder().id(PRODUCT_ID_ONE).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION)
            .price(PRODUCT_PRICE_TEN).build();
    public static final ProductCreateDto PRODUCT_CREATE_DTO_ONE = ProductCreateDto.builder().name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION)
            .price(PRODUCT_PRICE_TEN).image(PRODUCT_IMAGE_FILE).build();

    public static final ProductCreateDto PRODUCT_CREATE_DTO = ProductCreateDto.builder().producerId(PRODUCER_ID).categoryId(CATEGORY_ID).build();
    public static final ProductFilter PRODUCT_FILTER = ProductFilter.builder().name(PRODUCT_NAME).categoryId(CATEGORY_ID).producerId(PRODUCER_ID)
            .price(PRODUCT_PRICE_ONE_HUNDRED).minPrice(PRODUCT_PRICE_FIFTY).maxPrice(PRODUCT_PRICE_ONE_HUNDRED_FIFTY).build();
    public static final List<CartItemReadDto> CART_ITEM_READ_DTO_LIST = List.of(CART_ITEM_READ_DTO);
    public static final Category CATEGORY = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();
    public static final CategoryReadDto CATEGORY_READ_DTO = CategoryReadDto.builder().name(CATEGORY_NAME).build();
    public static final CartItem CART_ITEM = CartItem.builder().id(CART_ITEM_ID_TWO).quantity(QUANTITY_ONE).product(PRODUCT).cart(CART).build();
    public static final CartItem CART_ITEM_ONE = CartItem.builder().id(CART_ITEM_ID_ONE).build();
    public static final Order ORDER_ONE = Order.builder().id(ORDER_ID_ONE).build();
    public static final Order ORDER_TWO = Order.builder().id(ORDER_ID_TWO).build();
    public static final OrderReadDto ORDER_READ_DTO_ONE = OrderReadDto.builder().id(ORDER_ID_ONE).build();
    public static final OrderReadDto ORDER_READ_DTO_TWO = OrderReadDto.builder().id(ORDER_ID_TWO).build();
    public static final OrderItem ORDER_ITEM_ONE = OrderItem.builder().id(ORDER_ITEM_ID_ONE).quantity(QUANTITY_TWO).product(PRODUCT).build();
    public static final OrderItem ORDER_ITEM_TWO = OrderItem.builder().id(ORDER_ITEM_ID_TWO).quantity(QUANTITY_THREE).product(PRODUCT).build();
    public static final OrderItemReadDto ORDER_ITEM_READ_DTO_ONE = OrderItemReadDto.builder().id(ORDER_ITEM_ID_ONE).quantity(QUANTITY_TWO).build();
    public static final OrderItemReadDto ORDER_ITEM_READ_DTO_TWO = OrderItemReadDto.builder().id(ORDER_ITEM_ID_TWO).quantity(QUANTITY_THREE).build();
    public static final Producer PRODUCER = Producer.builder().id(PRODUCER_ID).name(PRODUCT_NAME).build();
    public static final ProducerReadDto PRODUCER_READ_DTO = ProducerReadDto.builder().id(PRODUCER_ID).name(PRODUCT_NAME).build();
    public static final List<Order> ORDER_LIST_ONE_EL = List.of(ORDER_ONE);
    public static final List<Order> ORDER_LIST_TWO_EL = List.of(ORDER_ONE, ORDER_TWO);
    public static final List<CartItem> CART_ITEM_LIST = List.of(CART_ITEM);
    public static final List<OrderItem> ORDER_ITEM_LIST_ZERO_EL = List.of();
    public static final List<OrderItem> ORDER_ITEM_LIST_TWO_EL = List.of(ORDER_ITEM_ONE, ORDER_ITEM_TWO);
    public static final List<OrderItem> ORDER_ITEM_LIST_ONE_EL = List.of(ORDER_ITEM_ONE);
    public static final List<Product> PRODUCT_LIST = List.of(PRODUCT_ONE, PRODUCT_TWO);
    public static final List<Category> CATEGORY_LIST = Collections.singletonList(CATEGORY);
    public static final User USER_ONE = User.builder().id(USER_ID_ONE).username(USER_NAME).password(USER_PASSWORD).build();
    public static final User USER_TWO = User.builder().id(USER_ID_TWO).username(USER_NAME).password(USER_PASSWORD).build();
    public static final UserReadDto USER_READ_DTO_ONE = UserReadDto.builder().id(USER_ID_ONE).username(USER_NAME).password(USER_PASSWORD).build();
    public static final UserCreateDto USER_CREATE_DTO_ONE = UserCreateDto.builder().username(USER_NAME).build();
    public static final User USER = User.builder().id(USER_ID_ONE).username(USER_NAME).email(USER_EMAIL)
            .role(Role.USER).enabled(ENABLED).build();
    public static final User USER_NO_ID = User.builder().username(USER_NAME).password(USER_PASSWORD).email(USER_EMAIL)
            .role(Role.USER).enabled(ENABLED).build();
    public static final User USER_NO_PASSWORD = User.builder().username(USER_NAME).email(USER_EMAIL)
            .role(Role.USER).enabled(ENABLED).build();
    public static final UserCreateDto USER_CREATE_DTO = UserCreateDto.builder().username(USER_NAME).email(USER_EMAIL)
            .rawPassword(USER_PASSWORD).role(Role.USER).enabled(ENABLED).build();
    public static final UserCreateDto USER_CREATE_DTO_MODIFIED = UserCreateDto.builder().username(USER_NAME).email("jackys@gmail.com")
            .role(Role.ADMIN).enabled(false).build();
    public static final UserCreateDto USER_CREATE_DTO_PASSWORD_NEW = UserCreateDto.builder().rawPassword(USER_PASSWORD_NEW).build();
    public static final UserReadDto USER_READ_DTO = UserReadDto.builder().id(USER_ID_ONE).username(USER_NAME).email(USER_EMAIL)
            .password(USER_PASSWORD).role(Role.USER).enabled(ENABLED).build();
    public static final UserInformation USER_INFORMATION = UserInformation.builder().id(USER_INFORMATION_ID)
            .firstName(USER_INFORMATION_FIRST_NAME).lastName(USER_INFORMATION_LAST_NAME)
            .phone(USER_INFORMATION_PHONE).address(USER_INFORMATION_ADDRESS).birthDate(USER_INFORMATION_BIRTH_DATE).user(USER).build();
    public static final UserInformationCreateDto USER_INFORMATION_CREATE_DTO = UserInformationCreateDto.builder()
            .firstName(USER_INFORMATION_FIRST_NAME).lastName(USER_INFORMATION_LAST_NAME)
            .phone(USER_INFORMATION_PHONE).address(USER_INFORMATION_ADDRESS).birthDate(USER_INFORMATION_BIRTH_DATE).build();
    public static final UserReadDto USER_READ_DTO_TWO = UserReadDto.builder().id(USER_ID_TWO).username(USER_NAME).password(USER_PASSWORD).build();
    public static final List<User> USER_LIST = List.of(USER_ONE, USER_TWO);
    public static final List<UserReadDto> USER_READ_DTO_LIST = List.of(USER_READ_DTO_ONE, USER_READ_DTO_TWO);
    public static final UserInformation USER_INFORMATION_ONE = UserInformation.builder().id(USER_INFORMATION_ID)
            .firstName(USER_INFORMATION_FIRST_NAME).lastName(USER_INFORMATION_LAST_NAME).build();
    public static final UserInformationReadDto USER_INFORMATION_READ_DTO_ONE = UserInformationReadDto.builder().id(USER_INFORMATION_ID)
            .firstName(USER_INFORMATION_FIRST_NAME).lastName(USER_INFORMATION_LAST_NAME).build();
    public static final UserInformationCreateDto USER_INFORMATION_CREATE_DTO_ONE = UserInformationCreateDto.builder()
            .firstName(USER_INFORMATION_FIRST_NAME).lastName(USER_INFORMATION_LAST_NAME).build();

}
