package ua.nure.ihnatov.SummaryTask.controller.dao;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.model.User;

public interface UserDAO extends DAO<User> {

    User checkUser(User user) throws DAOException;

    boolean blockUser(User user) throws DAOException;

    boolean unBlockUser(User user) throws DAOException;
}
