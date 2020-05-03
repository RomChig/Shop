package ua.nure.ihnatov.SummaryTask.controller.servlets.userServlets.identity;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.UserService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Messages;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Role;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/Registration")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter(Fields.USER_FIRST_NAME);
        String lastName = request.getParameter(Fields.USER_LAST_NAME);
        String login = request.getParameter(Fields.USER_LOGIN);
        String password = request.getParameter(Fields.USER_PASSWORD);
        try {
            User user = new UserService().create(new User(login, password, firstName, lastName, new Role(Fields.ROLE_ID_FOR_CUSTOMER)));
            if (!(user == null)) {
                response.sendRedirect(Paths.PATH_TO_MAIN_SERVLET);
            } else {
                throw new ServletException();
            }
        } catch (DAOException e) {
            if (e.getMessage().equals(Messages.ERROR_THING_ALREADY_EXIST_WITH_SUCH_PARAMETERS)) {
                request.setAttribute("errorUser", Messages.ERROR_USER);
                request.getRequestDispatcher(Paths.PATH_TO_ERROR_JSP).forward(request, response);
            } else {
                throw new ServletException();
            }
        }
    }
}
