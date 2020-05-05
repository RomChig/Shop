package ua.nure.ihnatov.SummaryTask.controller.servlets.userServlets.identity;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.UserService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Messages;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Authorization")
public class AuthorizationServlet extends HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(Fields.USER_LOGIN);
        String password = request.getParameter(Fields.USER_PASSWORD);
        User user;
        try {
            user = new UserService().checkUser(new User(login, password));
            if (!(user == null) && password.equals(user.getPassword())) {
                setUser(request, user);
                response.sendRedirect(Paths.PATH_TO_MAIN_SERVLET);
            } else {
                request.setAttribute("errorNotFoundUser", Messages.ERROR_NOT_FOUND_USER);
                request.getRequestDispatcher(Paths.PATH_TO_ERROR_JSP).forward(request, response);
            }
        } catch (DAOException e) {
            throw new ServletException();
        }
    }

    private void setUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        if (!(user == null)) {
            user.setPassword(null);
        }
        session.setAttribute(Fields.USER, user);

    }
}
