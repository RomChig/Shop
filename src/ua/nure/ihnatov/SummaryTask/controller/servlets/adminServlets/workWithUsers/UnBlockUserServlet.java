package ua.nure.ihnatov.SummaryTask.controller.servlets.adminServlets.workWithUsers;

import ua.nure.ihnatov.SummaryTask.controller.dao.UserDAO;
import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.UserService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UnBlockUser")
public class UnBlockUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.valueOf(req.getParameter(Fields.ID_FOR_EVERYBODY));
        UserDAO userService = new UserService();
        User user;
        try {
            user = userService.read(userId);
            user.setIsBlocked(false);
            boolean isUnBlocked = userService.unBlockUser(user);
            if (isUnBlocked) {
                resp.sendRedirect(Paths.PATH_TO_SHOW_USERS);
            }
        } catch (DAOException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Paths.PATH_TO_SHOW_USERS_JSP).forward(req,resp);
    }
}
