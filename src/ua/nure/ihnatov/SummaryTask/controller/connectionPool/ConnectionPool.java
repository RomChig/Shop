package ua.nure.ihnatov.SummaryTask.controller.connectionPool;

import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static ConnectionPool instance;
    private DataSource ds;
    private static final String NAME_OF_POOL = "jdbc.name";
    private static final String PATH_JNDI = "java.comp.env";

    private ConnectionPool() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup(MyPropWithinClasspath().getProperty(PATH_JNDI));
            ds = (DataSource) envContext.lookup(MyPropWithinClasspath().getProperty(NAME_OF_POOL));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Properties MyPropWithinClasspath() {
        Properties prop;
        try (InputStream is = this.getClass().getResourceAsStream(Paths.PROPERTIES)) {
            prop = new Properties();
            prop.load(is);
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
