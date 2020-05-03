package ua.nure.ihnatov.SummaryTask.controller.servlets;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.ProductService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/GetAllProducts")
public class GetAllProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products;
        try {
            products = new ProductService().getAll();
            request.setAttribute(Fields.LIST_OF_PRODUCTS, products);
            request.getRequestDispatcher(Paths.PATH_TO_MAIN_JSP).forward(request, response);
        } catch (DAOException e) {
            throw new ServletException();
        }
    }
}
