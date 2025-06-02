package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static Connection connection = null;
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/racebox.db";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Conectat la baza de date.");
            } catch (SQLException e) {
                System.out.println("Eroare la conectarea la baza de date: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexiunea la baza de date a fost inchisa.");
            } catch (SQLException e) {
                System.out.println("Eroare la inchiderea conexiunii cu baza de date: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}