package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            // Get the connection string from PropertyUtil
            String connectionString = DBPropertyUtil.getPropertyString();
            String username = "root"; // Assuming username is constant
            String password = "mahi";  // Assuming password is constant

            // Establish the connection
            connection = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
