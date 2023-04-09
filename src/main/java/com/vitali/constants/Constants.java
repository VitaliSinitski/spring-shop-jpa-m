package com.vitali.constants;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class Constants {
    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String COMMAND = "command";
    public final static String READ_BY_CATEGORY_ID = "read_by_category_id";
    public final static String READ_BY_CRITERIA = "read_by_criteria";
    public final static String READ_BY_CRITERIA_BEFORE = "read_by_criteria_before";
    public final static String LOGIN_ERROR = "loginError";
    public final static String SAME_EMAIL_ERROR = "sameEmailError";
    public final static String USER_REGISTERED_ERROR = "userIsRegistered";
    public final static String PRODUCT_IN_CART_NOTIFICATION = "productIsAlreadyInCart";
    public final static String USER_REGISTERED_NOTIFICATION = "userIsNowRegistered";
    public final static String USER_NOT_LOGGED_IN_ERROR = "userIsNotLoggedIn";
    public final static String USER_NOT_REGISTERED_ERROR = "userIsNotRegistered";
    public final static String NOT_CORRECT_PASSWORD_ERROR = "notCorrectPassword";
    public final static String NOTIFICATION = "notification";
    public final static String ERROR = "error";
    public final static String ERRORS = "errors";
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String SURNAME = "surname";
    public final static String USER = "user";
    public final static String ADDRESS = "address";
    public final static String PHONE = "phone";
    public final static String PASSWORD = "password";
    public final static String ROLE = "role";
    public final static String INFORMATION = "information";
    public final static String CUSTOMER_INFO = "customerInfo";
    public final static String CART = "cart";
    public final static String CART_ID = "cartId";
    public final static String CART_SIZE = "cartSize";
    public final static String PRODUCT = "product";
    public final static String PRODUCT_ID = "productId";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String AMOUNT = "amount";
    public static final String QUANTITY = "quantity";
    public static final String CATEGORY = "category";
    public static final String CATEGORY_ID = "categoryId";
    public static final String PRODUCER = "producer";
    public static final String PRODUCER_ID = "producerId";
    public static final String EMAIL = "email";
    public static final String IMAGE = "image";
    public static final String NEW_ORDER = "newOrder";
    public static final String ORDER_ITEMS = "orderItems";
    public static final String ORDER_ITEMS_IDS = "orderItemsIds";
    public static final String PRODUCT_LIST = "productList";
    public static final String CRITERIA_PRODUCTS = "criteriaProducts";
    public static final String PRODUCER_LIST = "producerList";
    public static final String CATEGORY_LIST = "categoryList";
    public static final String MAIN_JSP = "/WEB-INF/jsp/main.jsp";
    public static final String CART_JSP = "/WEB-INF/jsp/cart.jsp";
    public static final String PRODUCT_JSP = "/WEB-INF/jsp/product.jsp";
    public static final String REGISTRATION_JSP = "/WEB-INF/jsp/registration.jsp";
    public static final String PAGE_TEMPLATE_JSP = "/WEB-INF/jsp/page-template.jsp";
    public static final String CREATE_ORDER_JSP = "/WEB-INF/jsp/createOrder.jsp";
    public static final String CREATE_PRODUCT_JSP = "/WEB-INF/jsp/createProduct.jsp";
    public static final String CREATE_PRODUCER_JSP = "/WEB-INF/jsp/createProducer.jsp";
    public static final String HOME_JSP = "/WEB-INF/jsp/home.jsp";
    public static final String LIST_JSP = "/WEB-INF/jsp/list.jsp";
    public static final String ORDER_JSP = "/WEB-INF/jsp/order.jsp";
    public static final String FINISH_ORDER_JSP = "/WEB-INF/jsp/finishOrder.jsp";
    public static final String VIEW_JSP = "/WEB-INF/jsp/createView.jsp";
    public static final String LOGIN_JSP = "/WEB-INF/jsp/login.jsp";
    public static final String UPDATE_JSP = "/WEB-INF/jsp/update.jsp";
    public static final String CONTROLLER_LOGIN = "/controller?command=login";
    public static final Set<String> PUBLIC_PATH = Set.of("/controller");
    public static final String INDEX_JSP = "/index.jsp";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String RECORDS_PER_PAGE = "recordsPerPage";
    public static final String NUMBER_OF_PAGES = "numberOfPages";
    public static final String SORT_DIRECTION = "sortDirection";
    public static final String SORT_BY_COLUMN = "sortByColumn";
    public static final String ORDER_FIELD = "orderField";
    public static final String SEARCH_FIELD = "searchField";
    public static final String SEARCH_VALUE = "searchValue";
    public static final Role DEFAULT_ROLE = Role.USER;
    public static final Integer DEFAULT_QUANTITY = 1;
    public static final Integer DEFAULT_PAGE_NUMBER = 1;
    public static final Integer DEFAULT_RECORDS_PER_PAGE = 5;
    public static final String DEFAULT_SORT_BY_COLUMN = "id";
    public static final String DEFAULT_SEARCH_FIELD = "name";
    public static final SortDirection DEFAULT_SORT_DIRECTION = SortDirection.ASC;
    public static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.PENDING;
    public static final String IMAGE_FOLDER = "/product";
    public static final String IMAGE_BASE_PATH = "/image";
//    public String IMAGE_BASE_PATH = "src/main/webapp/WEB-INF/img/";

}
