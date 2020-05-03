package ua.nure.ihnatov.SummaryTask.controller.servlets.userServlets.workWithOrders;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.OrderService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Order;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/UserRoom")
public class UserRoomServlet extends HttpServlet {
    private static final String ORDER_LIST = "orders";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute(Fields.USER);
        List<Order> orderList;
        try {
            orderList = new OrderService().getAllByUserId(user.getId());
            request.setAttribute(ORDER_LIST, orderList);
            request.getRequestDispatcher(Paths.PATH_TO_USER_ROOM_JSP).forward(request, response);
        } catch (DAOException e) {
            throw new ServletException();
        }
    }
}
