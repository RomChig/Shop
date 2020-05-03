package ua.nure.ihnatov.SummaryTask.controller.servlets.adminServlets.workWithOrders;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.OrderService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/GetAllOrders")
public class GetAllOrdersServlet extends HttpServlet {
    private static final String LIST_OF_ORDERS = "orderList";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orderList;
        try {
            orderList = new OrderService().getAll();
            req.setAttribute(LIST_OF_ORDERS, orderList);
            req.getRequestDispatcher(Paths.PATH_TO_SHOW_ORDERS_JSP).forward(req, resp);
        } catch (DAOException e) {
            throw new ServletException();
        }
    }
}
