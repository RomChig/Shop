package ua.nure.ihnatov.SummaryTask.controller.utility;

public final class Queries {
    public static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT id, login, password, first_name, last_name, role_id, is_blocked " +
            "FROM users " +
            "WHERE login=? AND password=?";
    public static final String GET_USER_BY_USER_ID = "SELECT id, login," +
            "password, first_name, last_name, role_id, is_blocked " +
            "FROM users " +
            "WHERE id = ?";
    public static final String GET_ALL_USERS = "SELECT id, login," +
            "password, first_name, last_name, role_id, is_blocked " +
            "FROM users";
    public static final String GET_ALL_FROM_ROLES = "SELECT id, name FROM roles WHERE id = ?";
    public static final String GET_ALL_PRODUCTS = "SELECT id, name, type, capacity, color, price, currency, date_added, image_name " +
            "FROM products";
    public static final String GET_PRODUCT_BY_ID = "SELECT id, name, type, capacity, color, price, currency, date_added, image_name " +
            "FROM products " +
            "WHERE id = ?";
    public static final String GET_ALL_FROM_CART_BY_USER_ID = "SELECT id, product_id, quantity " +
            "FROM cart WHERE user_id = ?";
    public static final String GET_ORDERS_BY_USER_ID = "SELECT id, bill, user_id, status_id, date_created " +
            "FROM orders WHERE user_id = ?";
    public static final String GET_ORDER_BY_ID = "SELECT id, bill, user_id, status_id, date_created " +
            "FROM orders WHERE id = ?";
    public static final String GET_ALL_FROM_STATUSES = "SELECT id, name " +
            "FROM statuses WHERE id = ?";
    public static final String GET_ALL_ORDERS = "SELECT id, bill, user_id, status_id, date_created " +
            "FROM orders";
    public static final String GET_ALL_FROM_CART_BY_USER_PRODUCT_ID = "SELECT id, user_id, product_id, quantity " +
            "FROM cart " +
            "WHERE user_id = ? AND product_id = ?";
    public static final String GET_PRODUCT_ID_FROM_CART_BY_PRODUCT_ID = "SELECT product_id " +
            "FROM cart WHERE product_id = ? AND user_id = ?";
    public static final String GET_ALL_BY_ORDER_ID = "SELECT product_id, quantity FROM orders_products WHERE order_id = ?";
    public static final String CREATE_USER = "INSERT INTO users(login, password, first_name, last_name, role_id) " +
            "VALUES(?,?,?,?,?)";
    public static final String CREATE_PRODUCT = "INSERT INTO products(name, type, capacity, color, price, currency, date_added, image_name) " +
            "VALUES(?,?,?,?,?,?,CURRENT_DATE(),?)";
    public static final String CREATE_CART = "INSERT INTO cart(user_id, product_id) " +
            "VALUES (?,?)";
    public static final String CREATE_ORDER = "INSERT INTO orders(bill, user_id, date_created) " +
            "VALUES (?,?,CURRENT_DATE())";
    public static final String CREATE_ORDER_PRODUCT_RELATION = "INSERT INTO orders_products(order_id, product_id, quantity) VALUES (?,?,?)";
    public static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    public static final String DELETE_PRODUCT_FROM_CART = "DELETE FROM cart WHERE id = ?";
    public static final String DELETE_CART = "DELETE FROM cart WHERE user_id = ?";
    public static final String UPDATE_USER = "UPDATE users " +
            "SET login = ?, password = ?, first_name = ?, last_name = ?, role_id = ?, is_blocked = ? WHERE id = ?";
    public static final String UPDATE_PRODUCT_BY_ID = "UPDATE products SET name = ?, type = ?, capacity = ?, color = ?, price = ?, currency = ?,date_added = CURRENT_DATE(),image_name = ? " +
            "WHERE id = ?";
    public static final String UPDATE_ORDER = "UPDATE orders SET bill = ?, user_id = ?, status_id = ?, date_created = ? " +
            "WHERE id = ?";
    public static final String UPDATE_CART_BY_USER_PRODUCT_ID = "UPDATE cart SET quantity = ? " +
            "WHERE user_id = ? AND product_id = ?";

    private Queries() {
    }

}
