package ua.nure.ihnatov.SummaryTask.controller.utility;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Closer {
    private Closer() {
    }

    public static void close(PreparedStatement preparedStatement) throws DAOException {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                throw new DAOException(Messages.ERROR_WITH_CLOSING_PREPARE_STATEMENT);
            }
        }
    }

    public static void close(ResultSet rs) throws DAOException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                throw new DAOException(Messages.ERROR_WITH_CLOSING_RESULT_SET);
            }
        }
    }

    public static void close(PreparedStatement preparedStatement, ResultSet rs) throws DAOException {
        close(preparedStatement);
        close(rs);
    }
}
