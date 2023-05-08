package com.vitali.util;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Category;
import com.vitali.database.entities.Order;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.entities.Product;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.dto.category.CategoryReadDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class TestConstants {
    public static final Integer USER_ID = 1;
    public static final String USER_NAME = "Bob";
    public static final String USER_PASSWORD = "password";
    public static final CartItemReadDto CART_ITEM_READ_DTO = new CartItemReadDto();
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer CART_ID = 1;
    public static final Integer ORDER_ID_ONE = 1;
    public static final Integer ORDER_ID_TWO = 2;
    public static final Integer CART_ITEM_ID = 2;
    public static final Integer ORDER_ITEM_ID_ONE = 1;
    public static final Integer ORDER_ITEM_ID_TWO = 2;
    public static final String ORDER_INFORMATION = "Test Information";
    public static final Integer TIMES_ONE = 1;
    public static final Integer SIZE_ZERO = 0;
    public static final Integer SIZE_ONE = 1;
    public static final Integer SIZE_TWO = 2;
    public static final Integer QUANTITY_ONE = 1;
    public static final Integer QUANTITY_TWO = 2;
    public static final Integer QUANTITY_THREE = 3;
    public static final Integer QUANTITY_TEN = 10;
    public static final Integer PRODUCT_ID_ONE = 1;
    public static final Integer PRODUCT_ID_TWO = 2;
    public static final Integer PRODUCT_ID_THREE = 3;
    public static final String PRODUCT_NAME = "Test Product";
    public static final String CATEGORY_NAME = "New Category";
    public static final CartItemCreateDto CART_ITEM_CREATE_DTO = CartItemCreateDto.builder().quantity(QUANTITY_TWO).productId(PRODUCT_ID_ONE).cartId(CART_ID).build();
    public static final Product PRODUCT = Product.builder().price(BigDecimal.valueOf(QUANTITY_TEN)).build();
    public static final List<CartItemReadDto> CART_ITEM_READ_DTO_LIST = List.of(CART_ITEM_READ_DTO);
    public static final Cart CART = Cart.builder().id(CART_ID).build();
    public static final Category CATEGORY = Category.builder().name(CATEGORY_NAME).build();
    public static final CategoryReadDto CATEGORY_READ_DTO = CategoryReadDto.builder().name(CATEGORY_NAME).build();
    public static final CartItem CART_ITEM = CartItem.builder().id(CART_ITEM_ID).quantity(QUANTITY_ONE).product(PRODUCT).cart(CART).build();
    public static final Order ORDER_ONE = Order.builder().id(ORDER_ID_ONE).build();
    public static final Order ORDER_TWO = Order.builder().id(ORDER_ID_TWO).build();
    public static final OrderReadDto ORDER_READ_DTO_ONE = OrderReadDto.builder().id(ORDER_ID_ONE).build();
    public static final OrderReadDto ORDER_READ_DTO_TWO = OrderReadDto.builder().id(ORDER_ID_TWO).build();
    public static final OrderItem ORDER_ITEM_ONE = OrderItem.builder().id(ORDER_ITEM_ID_ONE).quantity(QUANTITY_TWO).product(PRODUCT).build();
    public static final OrderItem ORDER_ITEM_TWO = OrderItem.builder().id(ORDER_ITEM_ID_TWO).quantity(QUANTITY_THREE).product(PRODUCT).build();
    public static final OrderItemReadDto ORDER_ITEM_READ_DTO_ONE = OrderItemReadDto.builder().id(ORDER_ITEM_ID_ONE).quantity(QUANTITY_TWO).build();
    public static final OrderItemReadDto ORDER_ITEM_READ_DTO_TWO = OrderItemReadDto.builder().id(ORDER_ITEM_ID_TWO).quantity(QUANTITY_THREE).build();
    public static final List<Order> ORDER_LIST_ONE_EL = List.of(ORDER_ONE);
    public static final List<Order> ORDER_LIST_TWO_EL = List.of(ORDER_ONE, ORDER_TWO);
    public static final List<CartItem> CART_ITEM_LIST = List.of(CART_ITEM);
    public static final List<OrderItem> ORDER_ITEM_LIST_ZERO_EL = List.of();
    public static final List<OrderItem> ORDER_ITEM_LIST_TWO_EL = List.of(ORDER_ITEM_ONE, ORDER_ITEM_TWO);
    public static final List<OrderItem> ORDER_ITEM_LIST_ONE_EL = List.of(ORDER_ITEM_ONE);
}
