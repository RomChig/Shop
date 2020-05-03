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

@WebFilter(filterName = "BlockCreateOrderFilter",
        urlPatterns = "/CreateOrder")
public class BlockCreateOrderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        User user = (User) request.getSession(false).getAttribute(Fields.USER);
        String method = request.getMethod();
        HttpServletResponse response = (HttpServletResponse) resp;
        if ("GET".equals(method) && user == null) {
            req.setAttribute("errorLogin", Messages.ERROR);
            request.getRequestDispatcher(Paths.PATH_TO_ERROR_JSP).forward(request, response);
        } else {
            chain.doFilter(req, resp);
        }
    }
}
