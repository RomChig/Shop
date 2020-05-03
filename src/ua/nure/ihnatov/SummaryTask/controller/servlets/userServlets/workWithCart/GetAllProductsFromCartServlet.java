package ua.nure.ihnatov.SummaryTask.controller.servlets.userServlets.workWithCart;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.CartService;
import ua.nure.ihnatov.SummaryTask.controller.services.ProductService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Cart;
import ua.nure.ihnatov.SummaryTask.model.Product;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@WebServlet("/ProductCart")
public class GetAllProductsFromCartServlet extends HttpServlet {
    private static final String PRODUCT_ID = "productId";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession(false).getAttribute(Fields.USER);
        try {
            if (user == null) {
                wokWithCookies(req);
            } else {
                workWithDatabase(req, user);
            }
            req.getRequestDispatcher(Paths.PATH_TO_SHOW_CART_JSP).forward(req, resp);
        } catch (DAOException e) {
            throw new ServletException();
        }
    }

    private Cart getProductsFromCookies(HttpServletRequest req) throws DAOException {
        Cart cart = new Cart();
        Cookie[] cookies = req.getCookies();
        Map<Product, Long> productMap = new Hashtable<>();
        for (Cookie c : cookies) {
            if (c.getName().contains(PRODUCT_ID)) {
                productMap.put(getProduct(c.getName()), getQuantity(c.getValue()));
            }
        }
        cart.setMapProduct(productMap);
        return cart;
    }



    private Product getProduct(String cookieName) throws DAOException {
        int indexQuantity = 1;
        long productId;
        String[] s = cookieName.split("_");
        productId = Long.parseLong(s[indexQuantity]);
        return new ProductService().read(productId);
    }

    private Long getQuantity(String cookieValue) {
        int indexQuantity = 1;
        String[] s = cookieValue.split("_");
        return Long.parseLong(s[indexQuantity]);
    }

    private void wokWithCookies(HttpServletRequest req) throws DAOException {
        Cart cart = getProductsFromCookies(req);
        req.setAttribute(Fields.USER_CART, cart);
    }

    private void workWithDatabase(HttpServletRequest req, User user) throws DAOException {
        Cart cart = new CartService().getAllProductsByUserId(user.getId());
        req.getSession(false).setAttribute(Fields.USER_CART, cart);
    }
}