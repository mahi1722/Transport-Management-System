package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getPropertyString() {
        Properties props = new Properties();
        String connectionString = null;

        try {
            FileInputStream fis = new FileInputStream("db.properties");
            props.load(fis);

            // Read properties
            String hostname = props.getProperty("hostname");
            String dbname = props.getProperty("dbname");
            // String username = props.getProperty("username");
            // String password = props.getProperty("password");
            String portnumber = props.getProperty("portnumber");

            // Construct the connection string
            connectionString = String.format("jdbc:mysql://%s:%s/%s", hostname, portnumber, dbname);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connectionString;
    }
}
