package ua.nure.ihnatov.SummaryTask.controller.servlets.adminServlets.workWithProducts;

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

@WebServlet("/PreEdit")
public class PreEditProductServlet extends HttpServlet {
    private static final String PRODUCT = "product";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.valueOf(req.getParameter(Fields.PRODUCT_ID));
        Product product = getProduct(productId);
        if (!(product == null)) {
            req.getSession(false).setAttribute(PRODUCT, product);
            resp.sendRedirect(Paths.PATH_TO_EDIT_PRODUCT_SERVLET);
        }
    }

    private Product getProduct(Long productId) throws ServletException {
        try {
            return new ProductService().read(productId);
        } catch (DAOException e) {
            throw new ServletException();
        }
    }
}
