package com.vitali.constants;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class Constants {
    public String GET = "GET";
    public String POST = "POST";
    public String COMMAND = "command";
    public String READ_BY_CATEGORY_ID = "read_by_category_id";
    public String READ_BY_CRITERIA = "read_by_criteria";
    public String READ_BY_CRITERIA_BEFORE = "read_by_criteria_before";
    public String LOGIN_ERROR = "loginError";
    public String SAME_EMAIL_ERROR = "sameEmailError";
    public String USER_REGISTERED_ERROR = "userIsRegistered";
    public String PRODUCT_IN_CART_NOTIFICATION = "productIsAlreadyInCart";
    public String USER_REGISTERED_NOTIFICATION = "userIsNowRegistered";
    public String USER_NOT_LOGGED_IN_ERROR = "userIsNotLoggedIn";
    public String USER_NOT_REGISTERED_ERROR = "userIsNotRegistered";
    public String NOT_CORRECT_PASSWORD_ERROR = "notCorrectPassword";
    public String NOTIFICATION = "notification";
    public String ERROR = "error";
    public String ERRORS = "errors";
    public String ID = "id";
    public String NAME = "name";
    public String SURNAME = "surname";
    public String USER = "user";
    public String ADDRESS = "address";
    public String PHONE = "phone";
    public String PASSWORD = "password";
    public String ROLE = "role";
    public String INFORMATION = "information";
    public String CUSTOMER_INFO = "customerInfo";
    public String CART = "cart";
    public String CART_ID = "cartId";
    public String CART_SIZE = "cartSize";
    public String PRODUCT = "product";
    public String PRODUCT_ID = "productId";
    public String DESCRIPTION = "description";
    public String PRICE = "price";
    public String AMOUNT = "amount";
    public String QUANTITY = "quantity";
    public String CATEGORY = "category";
    public String CATEGORY_ID = "categoryId";
    public String PRODUCER = "producer";
    public String PRODUCER_ID = "producerId";
    public String EMAIL = "email";
    public String IMAGE = "image";
    public String NEW_ORDER = "newOrder";
    public String ORDER_ITEMS = "orderItems";
    public String ORDER_ITEMS_IDS = "orderItemsIds";
    public String PRODUCT_LIST = "productList";
    public String CRITERIA_PRODUCTS = "criteriaProducts";
    public String PRODUCER_LIST = "producerList";
    public String CATEGORY_LIST = "categoryList";
    public String MAIN_JSP = "/WEB-INF/jsp/main.jsp";
    public String CART_JSP = "/WEB-INF/jsp/cart.jsp";
    public String PRODUCT_JSP = "/WEB-INF/jsp/product.jsp";
    public String REGISTRATION_JSP = "/WEB-INF/jsp/registration.jsp";
    public String PAGE_TEMPLATE_JSP = "/WEB-INF/jsp/page-template.jsp";
    public String CREATE_ORDER_JSP = "/WEB-INF/jsp/createOrder.jsp";
    public String CREATE_PRODUCT_JSP = "/WEB-INF/jsp/createProduct.jsp";
    public String CREATE_PRODUCER_JSP = "/WEB-INF/jsp/createProducer.jsp";
    public String HOME_JSP = "/WEB-INF/jsp/home.jsp";
    public String LIST_JSP = "/WEB-INF/jsp/list.jsp";
    public String ORDER_JSP = "/WEB-INF/jsp/order.jsp";
    public String FINISH_ORDER_JSP = "/WEB-INF/jsp/finishOrder.jsp";
    public String VIEW_JSP = "/WEB-INF/jsp/createView.jsp";
    public String LOGIN_JSP = "/WEB-INF/jsp/login.jsp";
    public String UPDATE_JSP = "/WEB-INF/jsp/update.jsp";
    public String CONTROLLER_LOGIN = "/controller?command=login";
    public Set<String> PUBLIC_PATH = Set.of("/controller");
    public String INDEX_JSP = "/index.jsp";
    public String CURRENT_PAGE = "currentPage";
    public String RECORDS_PER_PAGE = "recordsPerPage";
    public String NUMBER_OF_PAGES = "numberOfPages";
    public String SORT_DIRECTION = "sortDirection";
    public String SORT_BY_COLUMN = "sortByColumn";
    public String ORDER_FIELD = "orderField";
    public String SEARCH_FIELD = "searchField";
    public String SEARCH_VALUE = "searchValue";
    public Role DEFAULT_ROLE = Role.USER;
    public Integer DEFAULT_QUANTITY = 1;
    public Integer DEFAULT_PAGE_NUMBER = 1;
    public Integer DEFAULT_RECORDS_PER_PAGE = 5;
    public String DEFAULT_SORT_BY_COLUMN = "id";
    public String DEFAULT_SEARCH_FIELD = "name";
    public SortDirection DEFAULT_SORT_DIRECTION = SortDirection.ASC;
    public String IMAGE_FOLDER = "/product";
    public String IMAGE_BASE_PATH = "/image";
//    public String IMAGE_BASE_PATH = "src/main/webapp/WEB-INF/img/";

}
