package ua.nure.ihnatov.SummaryTask.controller.servlets.adminServlets.workWithOrders;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.OrderService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SetStatusForOrder")
public class SetStatusForOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long orderId = Long.valueOf(req.getParameter(Fields.ORDER_ID));
        String setStatus = req.getParameter(Fields.SET_STATUS);
        try {
            Order order = new OrderService().read(orderId);
            if ("paid".equals(setStatus)) {
                boolean setPaid = new OrderService().setPaidStatusForOrder(order);
                if (setPaid) {
                    resp.sendRedirect(Paths.PATH_TO_SET_STATUS_FOR_ORDER_SERVLET);
                }
            } else if ("canceled".equals(setStatus)) {
                boolean setCanceled = new OrderService().setCanceledStatusForOrder(order);
                if (setCanceled) {
                    resp.sendRedirect(Paths.PATH_TO_SET_STATUS_FOR_ORDER_SERVLET);
                }
            }
        } catch (DAOException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Paths.PATH_TO_SHOW_ORDERS_SERVLET).forward(req, resp);
    }
}

