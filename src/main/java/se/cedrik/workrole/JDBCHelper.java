package se.cedrik.workrole;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCHelper {
    private static Properties properties = new Properties();

    static {

        String propertiesFile = "application.properties";

        try (InputStream stream = JDBCHelper.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            if (stream == null) {
                throw new IOException("Properties-filen '" + propertiesFile + "' hittades inte.");
            }
            properties.load(stream);
            System.out.println("Laddade konfiguration från: " + propertiesFile);
        } catch (IOException e) {
            throw new RuntimeException("Kunde inte ladda properties-filen", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        System.out.println("Ansluter till databas med URL: " + url);
        return DriverManager.getConnection(url, user, password);
    }
}
