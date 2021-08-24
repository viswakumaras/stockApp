import exception.DBException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;


public class ConnectionUtil {
    private ConnectionUtil() {
        //default Constructor
    }
    private static String driverClass = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://localhost/postgres";
    private static String username = "postgres";
    private static String password = "Viswa@2000";

    public static Connection createConnection() {

        Connection connection = null;
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
           } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new DBException("Unable to get the database connection");
           }

        return connection;
        //            Context initContext = new InitialContext();
//            Context envContext  = (Context)initContext.lookup("java:/comp/env");
//            DataSource ds = (DataSource)envContext.lookup("jdbc/PostgreSQL");


        //   Connection connection1 = ds.getConnection();
    }

    public static void closeConnection(ResultSet rs, PreparedStatement pst, Connection connection) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DBException("There no connection to close");
        }
    }
}
