package database;

import java.net.URL;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class DBconnection {
    private static final String URL_STRING;

    static {
        try {
            URL resource = DBconnection.class.getClassLoader().getResource("database.db");
            if (resource == null) throw new RuntimeException("database.db not found in resources.");
            File dbFile = new File(resource.toURI());
            URL_STRING = "jdbc:sqlite:" + dbFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database path", e);
        }
    }

    private static Connection connection;

    private DBconnection() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL_STRING);
                System.out.println("Connected to database.");
            } catch (SQLException e) {
                System.out.println("Database connection failed!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}