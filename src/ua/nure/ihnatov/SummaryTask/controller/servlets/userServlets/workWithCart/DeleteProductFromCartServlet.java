package ua.nure.ihnatov.SummaryTask.controller.servlets.userServlets.workWithCart;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.CartService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Cart;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteFromCart")
public class DeleteProductFromCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession(false).getAttribute(Fields.USER);
        String productId = req.getParameter(Fields.PRODUCT_ID);
        Long quantity = Long.valueOf(req.getParameter(Fields.PRODUCT_QUANTITY));
        try {
            if (user == null) {
                removeProductFromCookie(req, resp, productId);
            } else {
                removeProductFromDatabase(req, resp, productId, user.getId(), quantity);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    private void removeProductFromCookie(HttpServletRequest req, HttpServletResponse resp, String productId) throws IOException {
        int q = 1;
        Cookie cookie = getCookie(Long.valueOf(productId), req);
        if (!(cookie == null)) {
            long value = checkCookieValue(cookie.getValue());
            if (value == q) {
                Cookie newCookie = new Cookie(cookie.getName(), Fields.EMPTY_COOKIE_VALUE);
                newCookie.setMaxAge(0);
                cookie = newCookie;
            } else {
                cookie = reduceQuantity(cookie.getName(), cookie.getValue());
            }
        }
        resp.addCookie(cookie);
        resp.sendRedirect(Paths.PATH_TO_CART_SERVLET);
    }

    private long checkCookieValue(String cookieValue) {
        int indexQuantity = 1;
        String[] s = cookieValue.split("_");
        return Long.parseLong(s[indexQuantity]);
    }

    private Cookie getCookie(Long productId, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().contains(Fields.PRODUCT_ID_FOR_COOKIES) && c.getName().contains(productId.toString())) {
                return new Cookie(c.getName(), c.getValue());
            }
        }
        return null;
    }

    private Cookie reduceQuantity(String cookieName, String cookieValue) {
        int q = 1;
        int indexQuantity = 1;
        String[] s = cookieValue.split("_");
        long quantity = Long.parseLong(s[indexQuantity]) - q;
        return new Cookie(cookieName, Fields.PRODUCT_QUANTITY + "_" + quantity);
    }


    private void removeProductFromDatabase(HttpServletRequest req, HttpServletResponse resp, String productId, Long userId, Long quantity) throws IOException, DAOException {
        int q = 1;
        Long idProduct = Long.valueOf(productId);
        CartService cartService = new CartService();
        Cart cart = cartService.getByUserProductId(userId, idProduct);
        if (quantity == q) {
            boolean isRemoved = cartService.delete(cart);
            if (isRemoved) {
                resp.sendRedirect(Paths.PATH_TO_CART_SERVLET);
            }
        } else {
            boolean isReduced = cartService.reduceQuantity(cart);
            if (isReduced) {
                resp.sendRedirect(Paths.PATH_TO_CART_SERVLET);
            }
        }
    }
}

