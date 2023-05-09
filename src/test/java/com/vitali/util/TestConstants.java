package com.vitali.util;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Category;
import com.vitali.database.entities.Order;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.entities.Producer;
import com.vitali.database.entities.Product;
import com.vitali.database.entities.User;
import com.vitali.database.entities.UserInformation;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.dto.category.CategoryReadDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.dto.producer.ProducerReadDto;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductFilter;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import lombok.experimental.UtilityClass;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class TestConstants {
    public static final Integer USER_ID = 1;
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
    public static final Integer CART_ITEM_ID = 2;
    public static final Integer ORDER_ITEM_ID_ONE = 1;
    public static final Integer ORDER_ITEM_ID_TWO = 2;
    public static final Integer TIMES_ONE = 1;
    public static final Integer SIZE_ZERO = 0;
    public static final Integer SIZE_ONE = 1;
    public static final Integer SIZE_TWO = 2;
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
    public static final String USER_NAME = "Jack";
    public static final String USER_PASSWORD = "password";
    public static final String USER_INFORMATION_FIRST_NAME = "Jack";
    public static final String USER_INFORMATION_LAST_NAME = "Black";
    public static final String ORDER_INFORMATION = "Information";
    public static final String PRODUCT_NAME = "Test Product";
    public static final String PRODUCT_DESCRIPTION = "Test product description";
    public static final String CATEGORY_NAME = "New Category";
    public static final String PRODUCT_IMAGE_STRING = "image";
    public static final String PRODUCER_NAME = "New Producer";
    public static final Cart CART = Cart.builder().id(CART_ID_ONE).build();
    public static final CartItemReadDto CART_ITEM_READ_DTO = new CartItemReadDto();
    public static final CartItemCreateDto CART_ITEM_CREATE_DTO = CartItemCreateDto.builder().quantity(QUANTITY_TWO)
            .productId(PRODUCT_ID_ONE).cartId(CART_ID_ONE).build();
    public static final byte[] PRODUCT_IMAGE_BYTES = new byte[] { 0x12, 0x34, 0x56, 0x78 };
    public static final MockMultipartFile PRODUCT_IMAGE_FILE = new MockMultipartFile("image", "test.jpg", "image/jpeg", PRODUCT_IMAGE_BYTES);
    public static final Product PRODUCT_ONE = Product.builder().id(PRODUCT_ID_ONE).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION)
            .price(PRODUCT_PRICE_TEN).build();
    public static final Product PRODUCT_ONE_WITH_IMAGE_STRING = Product.builder().id(PRODUCT_ID_ONE).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION)
            .image(PRODUCT_IMAGE_STRING).price(PRODUCT_PRICE_TEN).build();
    public static final Product PRODUCT_TWO = Product.builder().id(PRODUCT_ID_TWO).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION).build();
    public static final Product PRODUCT = Product.builder().price(PRODUCT_PRICE_TEN).build();
    public static final Product UPDATED_PRODUCT_ONE = Product.builder().id(PRODUCT_ID_ONE).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION).price(PRODUCT_PRICE_TEN).build();
    public static final ProductReadDto PRODUCT_READ_DTO_ONE = ProductReadDto.builder().id(PRODUCT_ID_ONE).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION)
            .price(PRODUCT_PRICE_TEN).build();
    public static final ProductCreateDto PRODUCT_CREATE_DTO_ONE = ProductCreateDto.builder().name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION)
            .price(PRODUCT_PRICE_TEN).image(PRODUCT_IMAGE_FILE).build();
    public static final ProductFilter PRODUCT_FILTER = ProductFilter.builder().name(PRODUCT_NAME).categoryId(CATEGORY_ID).producerId(PRODUCER_ID)
            .price(PRODUCT_PRICE_ONE_HUNDRED).minPrice(PRODUCT_PRICE_FIFTY).maxPrice(PRODUCT_PRICE_ONE_HUNDRED_FIFTY).build();
    public static final List<CartItemReadDto> CART_ITEM_READ_DTO_LIST = List.of(CART_ITEM_READ_DTO);
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
    public static final User USER = User.builder().id(USER_ID).username(USER_NAME).password(USER_PASSWORD).build();
    public static final UserInformation USER_INFORMATION = UserInformation.builder().id(USER_INFORMATION_ID)
            .firstName(USER_INFORMATION_FIRST_NAME).lastName(USER_INFORMATION_LAST_NAME).build();
    public static final UserInformationReadDto USER_INFORMATION_READ_DTO = UserInformationReadDto.builder().id(USER_INFORMATION_ID)
            .firstName(USER_INFORMATION_FIRST_NAME).lastName(USER_INFORMATION_LAST_NAME).build();
    public static final UserInformationCreateDto USER_INFORMATION_CREATE_DTO = UserInformationCreateDto.builder()
            .firstName(USER_INFORMATION_FIRST_NAME).lastName(USER_INFORMATION_LAST_NAME).build();

}
