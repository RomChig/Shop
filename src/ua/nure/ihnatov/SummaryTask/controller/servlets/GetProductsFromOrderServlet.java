package ua.nure.ihnatov.SummaryTask.controller.servlets;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.OrderService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/OrdersProducts")
public class GetProductsFromOrderServlet extends HttpServlet {
    private static final String PRODUCT_MAP = "productMap";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long orderId = Long.valueOf(req.getParameter(Fields.ID_FOR_EVERYBODY));
        try {
            Map<Product, Long> productMap = new OrderService().getAllByOrderId(orderId);
            if (!(productMap == null)) {
                req.getSession(false).setAttribute(PRODUCT_MAP, productMap);
                resp.sendRedirect(Paths.PATH_TO_SHOW_PRODUCTS_FROM_ORDER_SERVLET);
            }
        } catch (DAOException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Paths.PATH_TO_SHOW_PRODUCTS_FROM_ORDER_JSP).forward(req, resp);
    }
}
