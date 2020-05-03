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
@WebServlet("/DeleteProduct")
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter(Fields.PRODUCT_ID));
        try {
            boolean isRemove = new ProductService().delete(new Product(productId));
            if (isRemove){
                resp.sendRedirect(Paths.PATH_TO_MAIN_SERVLET);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
