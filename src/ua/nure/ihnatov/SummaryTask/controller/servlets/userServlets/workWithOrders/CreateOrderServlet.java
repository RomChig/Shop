package ua.nure.ihnatov.SummaryTask.controller.servlets.userServlets.workWithOrders;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.OrderService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Cart;
import ua.nure.ihnatov.SummaryTask.model.Order;
import ua.nure.ihnatov.SummaryTask.model.Product;
import ua.nure.ihnatov.SummaryTask.model.Status;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/CreateOrder")
public class CreateOrderServlet extends HttpServlet {
    private static final String USER_CART = "userCart";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double bill = 0.0;
        User user = (User) req.getSession(false).getAttribute(Fields.USER);
        Cart cart = (Cart) req.getSession(false).getAttribute(USER_CART);
        try {
            for (Map.Entry<Product, Long> map : cart.getMapProduct().entrySet()) {
                bill += (map.getKey().getPrice() * map.getValue());
            }
            Order order = new OrderService().create(new Order(bill, user, new Status(Fields.STATUS_ID_FOR_REGISTERED_ORDERS)));
            boolean isSet = new OrderService().createRelationOrderAndCart(order, cart);
            if (isSet) {
                resp.sendRedirect(Paths.PATH_TO_MAIN_SERVLET);
            }
        } catch (DAOException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
