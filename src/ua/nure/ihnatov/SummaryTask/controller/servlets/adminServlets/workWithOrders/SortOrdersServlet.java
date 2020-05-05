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
import java.util.stream.Collectors;

@WebServlet("/SortOrders")
public class SortOrdersServlet extends HttpServlet {
    private static final String LIST_OF_ORDERS = "orderList";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date dateFrom = Date.valueOf(req.getParameter("dateFrom"));
        Date dateTo = Date.valueOf(req.getParameter("dateTo"));
        try {
            List<Order> orderList = new OrderService().getAll();
            List<Order> newOrderList;
            newOrderList = orderList.stream()
                    .filter(x -> (x.getDateCreated().after(dateFrom) && x.getDateCreated().before(dateTo)) || (dateTo.toString().equals(x.getDateCreated().toString())))
                    .collect(Collectors.toList());
            Collections.reverse(newOrderList);
            orderList = newOrderList;
            req.getSession(false).setAttribute(LIST_OF_ORDERS, orderList);
            resp.sendRedirect(Paths.PATH_TO_SORT_ORDERS_SERVLET);
        } catch (
                DAOException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Paths.PATH_TO_SHOW_ORDERS_JSP).forward(req, resp);
    }
}
