package ua.nure.ihnatov.SummaryTask.controller.servlets.userServlets.workWithCart;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.CartService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteCart")
public class DeleteCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession(false).getAttribute(Fields.USER);
        try {
            if (user == null) {
                boolean isRemoved = deleteCartFromCookies(req, resp);
                if (isRemoved) {
                    resp.sendRedirect(Paths.PATH_TO_CART_SERVLET);
                }
            } else {
                boolean isDeleted = deleteCartFromDataBase(user.getId());
                if (isDeleted) {
                    resp.sendRedirect(Paths.PATH_TO_CART_SERVLET);
                }
            }
        } catch (DAOException e) {
            throw new ServletException();
        }
    }

    private boolean deleteCartFromCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().contains(Fields.PRODUCT_ID_FOR_COOKIES)) {
                Cookie newCookie = new Cookie(c.getName(), Fields.EMPTY_COOKIE_VALUE);
                newCookie.setMaxAge(0);
                resp.addCookie(newCookie);
            }
        }
        return true;
    }

    private boolean deleteCartFromDataBase(Long userId) throws DAOException {
        return new CartService().deleteByUserId(userId);
    }
}
