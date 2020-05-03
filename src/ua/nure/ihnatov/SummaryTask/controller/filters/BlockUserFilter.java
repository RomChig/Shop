package ua.nure.ihnatov.SummaryTask.controller.filters;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.UserService;
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

@WebFilter(filterName = "BlockUserFilter",
        urlPatterns = "/Authorization")
public class BlockUserFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String login = req.getParameter(Fields.USER_LOGIN);
        String password = req.getParameter(Fields.USER_PASSWORD);
        try {
            User user = new UserService().checkUser(new User(login, password));
            if (user == null) {
                chain.doFilter(req, resp);
            } else {
                HttpServletRequest request = (HttpServletRequest) req;
                String method = request.getMethod();
                if ("POST".equals(method) && user.getIsBlocked()) {
                    HttpServletResponse response = (HttpServletResponse) resp;
                    req.setAttribute("errorBlocked", Messages.ERROR_USER_WAS_BLOCKED);
                    request.getRequestDispatcher(Paths.PATH_TO_ERROR_JSP).forward(request, response);
                } else if ("GET".equals(method)) {
                    HttpServletResponse response = (HttpServletResponse) resp;
                    response.sendRedirect(Paths.PATH_TO_MAIN_SERVLET);
                } else {
                    chain.doFilter(req, resp);
                }
            }
        } catch (DAOException e) {
            throw new ServletException();
        }
    }
}
