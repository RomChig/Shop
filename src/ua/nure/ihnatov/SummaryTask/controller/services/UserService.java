package ua.nure.ihnatov.SummaryTask.controller.services;

import ua.nure.ihnatov.SummaryTask.controller.connectionPool.ConnectionPool;
import ua.nure.ihnatov.SummaryTask.controller.dao.UserDAO;
import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.utility.Closer;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Messages;
import ua.nure.ihnatov.SummaryTask.controller.utility.Queries;
import ua.nure.ihnatov.SummaryTask.model.Role;
import ua.nure.ihnatov.SummaryTask.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserService implements UserDAO {

    @Override
    public User checkUser(User user) throws DAOException {
        PreparedStatement authorisationPrepareStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            authorisationPrepareStatement = connection.prepareStatement(Queries.GET_USER_BY_LOGIN_AND_PASSWORD);
            authorisationPrepareStatement.setString(k++, user.getLogin());
            authorisationPrepareStatement.setString(k, user.getPassword());
            resultSet = authorisationPrepareStatement.executeQuery();
            if (resultSet.next()) {
                 return extractUser(resultSet, user);
            }
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(authorisationPrepareStatement, resultSet);
        }
        return null;
    }

    @Override
    public boolean blockUser(User user) throws DAOException {
        return update(user);
    }

    @Override
    public boolean unBlockUser(User user) throws DAOException {
        return update(user);
    }

    private Role getRoleById(Long role_id) throws SQLException, DAOException {
        Role role = null;
        PreparedStatement getRoleByIdPrepareStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            getRoleByIdPrepareStatement = connection.prepareStatement(Queries.GET_ALL_FROM_ROLES);
            getRoleByIdPrepareStatement.setLong(k, role_id);
            resultSet = getRoleByIdPrepareStatement.executeQuery();
            if (resultSet.next()) {
                role = new Role();
                role.setName(resultSet.getString(Fields.NAME_FOR_EVERYBODY));
                role.setId(resultSet.getLong(Fields.ID_FOR_EVERYBODY));
            }
            return role;
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            Closer.close(getRoleByIdPrepareStatement, resultSet);
        }
    }

    @Override
    public User create(User user) throws DAOException {
        PreparedStatement registrationPrepareStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            registrationPrepareStatement = connection.prepareStatement(Queries.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            registrationPrepareStatement.setString(k++, user.getLogin());
            registrationPrepareStatement.setString(k++, user.getPassword());
            registrationPrepareStatement.setString(k++, user.getFirstName());
            registrationPrepareStatement.setString(k++, user.getLastName());
            registrationPrepareStatement.setLong(k, user.getRole().getId());
            if (registrationPrepareStatement.executeUpdate() > 0) {
                resultSet = registrationPrepareStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(Fields.COLUMN_INDEX_FOR_ID));
                }
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new DAOException(Messages.ERROR_THING_ALREADY_EXIST_WITH_SUCH_PARAMETERS, ex);
        } catch (SQLException e) {
            throw new DAOException(Messages.ERROR_SOMETHING_WAS_WRONG_DUE_OPERATION);
        } finally {
            Closer.close(registrationPrepareStatement, resultSet);
        }
        return user;
    }

    private User extractUser(ResultSet resultSet, User user) throws SQLException, DAOException {
        user.setPassword(resultSet.getString(Fields.USER_PASSWORD));
        user.setFirstName(resultSet.getString(Fields.USER_FIRST_NAME));
        user.setLastName(resultSet.getString(Fields.USER_LAST_NAME));
        user.setLogin(resultSet.getString(Fields.USER_LOGIN));
        user.setRole(getRoleById(resultSet.getLong(Fields.USER_ROLE_ID)));
        user.setId(resultSet.getLong(Fields.ID_FOR_EVERYBODY));
        user.setIsBlocked(resultSet.getBoolean(Fields.USER_IS_BLOCKED));
        return user;
    }

    @Override
    public User read(Long userId) throws DAOException {
        ResultSet resultSet = null;
        User user;
        PreparedStatement preparedStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            preparedStatement = connection.prepareStatement(Queries.GET_USER_BY_USER_ID);
            preparedStatement.setLong(k, userId);
            resultSet = preparedStatement.executeQuery();
            user = new User();
            if (resultSet.next()) {
                extractUser(resultSet, user);
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(preparedStatement, resultSet);
        }
    }

    @Override
    public List<User> getAll() throws DAOException {
        PreparedStatement getAllUsersPrepareStatement = null;
        ResultSet resultSet = null;
        List<User> userList;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            getAllUsersPrepareStatement = connection.prepareStatement(Queries.GET_ALL_USERS);
            resultSet = getAllUsersPrepareStatement.executeQuery();
            userList = Collections.synchronizedList(new ArrayList<>());
            while (resultSet.next()) {
                User user = new User();
                extractUser(resultSet, user);
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(getAllUsersPrepareStatement, resultSet);
        }
    }

    @Override
    public boolean update(User user) throws DAOException {
        PreparedStatement updateUserPrepareStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            updateUserPrepareStatement = connection.prepareStatement(Queries.UPDATE_USER);
            updateUserPrepareStatement.setString(k++, user.getLogin());
            updateUserPrepareStatement.setString(k++, user.getPassword());
            updateUserPrepareStatement.setString(k++, user.getFirstName());
            updateUserPrepareStatement.setString(k++, user.getLastName());
            updateUserPrepareStatement.setLong(k++, user.getRole().getId());
            updateUserPrepareStatement.setBoolean(k++, user.getIsBlocked());
            updateUserPrepareStatement.setLong(k, user.getId());
            return updateUserPrepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(updateUserPrepareStatement);
        }
    }

    @Override
    public boolean delete(User entity) throws DAOException {
        return false;
    }

}
