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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/SortOrder")
public class SortOrderServlet extends HttpServlet {
    private static final String LIST_OF_ORDERS = "orderList";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date dateFrom = Date.valueOf(req.getParameter("dateFrom"));
        Date dateTo = Date.valueOf(req.getParameter("dateTo"));
        try {
            List<Order> orderList = new OrderService().getAll();
            List<Order> newOrderList = new ArrayList<>();
            for (Order o : orderList) {
//                if (((o.getDateCreated().equals(dateFrom) || o.getDateCreated().equals(dateTo))) | (o.getDateCreated().after(dateFrom) && (o.getDateCreated().before(dateTo)))) {
//                    newOrderList.add(o);
//                }
                if (o.getDateCreated().after(dateFrom) && o.getDateCreated().before(dateTo)) {
                    newOrderList.add(o);
                } else if (o.getDateCreated().equals(dateFrom)) {
                    newOrderList.add(o);
                }
            }
//            newOrderList.sort(Comparator.comparing(Order::getDateCreated));
            Collections.reverse(newOrderList);
            orderList = newOrderList;
            req.getSession(false).setAttribute(LIST_OF_ORDERS, orderList);
            resp.sendRedirect("/SortOrder");
        } catch (
                DAOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Paths.PATH_TO_SHOW_ORDERS_JSP).forward(req, resp);
    }
}
