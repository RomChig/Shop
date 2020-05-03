package ua.nure.ihnatov.SummaryTask.controller.servlets.adminServlets.workWithUsers;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.UserService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/GetAllUsers")
public class GetAllUsersServlet extends HttpServlet {
    private static final String LIST_OF_USERS = "users";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users;
        try {
            users = new UserService().getAll();
            request.getSession().setAttribute(LIST_OF_USERS, users);
            request.getRequestDispatcher(Paths.PATH_TO_SHOW_USERS_JSP).forward(request, response);
        } catch (DAOException e) {
            throw new ServletException();
        }
    }
}
