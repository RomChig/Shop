package ua.nure.ihnatov.SummaryTask.controller.filters;

import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Messages;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CheckAccessFilter",
        urlPatterns = {"/GetAllUsers", "/GetAllOrders", "/SetStatusForOrder", "/AddProduct", "/Edit", "/PreEdit", "/BlockUser", "/UnBlockUser"})
public class CheckAccessFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        User user = (User) request.getSession(false).getAttribute(Fields.USER);
        String method = request.getMethod();
        HttpServletResponse response = (HttpServletResponse) resp;
        if (user == null) {
            req.setAttribute("errorLogin", Messages.ERROR);
            request.getRequestDispatcher(Paths.PATH_TO_ERROR_JSP).forward(request, response);
        } else if (("POST".equals(method) || "GET".equals(method)) && user.getRole().getId() == Fields.ROLE_ID_FOR_ADMIN) {
            chain.doFilter(req, resp);
        } else {
            req.setAttribute("access", Messages.ERROR_ACCESS_DENIED);
            request.getRequestDispatcher(Paths.PATH_TO_ERROR_JSP).forward(request, response);
        }
    }
}
