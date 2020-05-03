package ua.nure.ihnatov.SummaryTask.controller.servlets.adminServlets.workWithProducts;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.utility.FileUpload;
import ua.nure.ihnatov.SummaryTask.controller.utility.Messages;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AddProduct")
@MultipartConfig
public class AddProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            boolean isAdded = new FileUpload().actionWithProduct(req);
            if (isAdded) {
                resp.sendRedirect(Paths.PATH_TO_MAIN_SERVLET);
            } else {
                throw new ServletException();
            }
        } catch (DAOException e) {
            if (e.getMessage().equals(Messages.ERROR_THING_ALREADY_EXIST_WITH_SUCH_PARAMETERS)) {
                req.setAttribute("errorProduct", Messages.ERROR_PRODUCT);
                req.getRequestDispatcher(Paths.PATH_TO_ERROR_JSP).forward(req, resp);
            } else {
                throw new ServletException();
            }
        }
    }
}

