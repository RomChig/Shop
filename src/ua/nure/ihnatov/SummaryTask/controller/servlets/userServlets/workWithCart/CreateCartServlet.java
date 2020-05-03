package ua.nure.ihnatov.SummaryTask.controller.servlets.userServlets.workWithCart;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.CartService;
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

@WebServlet("/AddProductToCart")
public class CreateCartServlet extends HttpServlet {
    private static final String PRODUCT_ID = "productId";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = (User) req.getSession(false).getAttribute(Fields.USER);
            Long productId = Long.parseLong(req.getParameter(Fields.PRODUCT_ID));
            if (user == null) {
                workWithCookies(productId, req, resp);
            } else {
                workWithDatabase(user, productId, resp);
            }
        } catch (DAOException | NumberFormatException e) {
            throw new ServletException();
        }
    }

    private void workWithCookies(Long productId, HttpServletRequest req, HttpServletResponse resp) throws IOException, DAOException {
        Cookie cookie = getCookie(productId, req);
        final long valueQuantity = 1;
        if (!(cookie == null)) {
            cookie = raiseQuantity(cookie.getName(), cookie.getValue());
        } else {
            cookie = new Cookie(PRODUCT_ID + "_" + productId.toString(), Fields.PRODUCT_QUANTITY + "_" + valueQuantity);
        }
        resp.addCookie(cookie);
        resp.sendRedirect(Paths.PATH_TO_MAIN_SERVLET);
    }

    private Cookie getCookie(Long productId, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().contains(PRODUCT_ID) && c.getName().contains(productId.toString())) {
                return new Cookie(c.getName(), c.getValue());
            }
        }
        return null;
    }

    private Cookie raiseQuantity(String cookieName, String cookieValue) {
        final int indexQuantity = 1;
        String[] s = cookieValue.split("_");
        long quantity = Long.parseLong(s[indexQuantity]);
        return new Cookie(cookieName, Fields.PRODUCT_QUANTITY + "_" + ++quantity);
    }

    private void workWithDatabase(User user, Long productId, HttpServletResponse resp) throws
            DAOException, IOException {
        Cart cart = new Cart(new User(user.getId()), new Product(productId));
        boolean isExist = new CartService().isProductExist(new Product(productId), user);
        if (isExist) {
            boolean isRaised = new CartService().raiseQuantity(cart);
            if (isRaised) {
                resp.sendRedirect(Paths.PATH_TO_MAIN_SERVLET);
            }
        } else {
            Cart cart1 = new CartService().create(cart);
            if (!(cart1 == null)) {
                resp.sendRedirect(Paths.PATH_TO_MAIN_SERVLET);
            }
        }
    }
}
