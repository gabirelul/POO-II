package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Owner;
import model.Vehicle;
import model.Car;
import model.Motorcycle;

public class VehicleDAO {

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS vehicle (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                type TEXT NOT NULL,       -- "CAR" or "MOTORCYCLE"
                brand TEXT NOT NULL,
                model TEXT NOT NULL,
                year INTEGER,
                price REAL
            );
        """;
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'vehicle' created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertVehicle(String type, String brand, String model, int year, double price, Integer doors, Integer ownerId) {
        String sql = "INSERT INTO vehicle(type, brand, model, year, price, doors, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, brand);
            pstmt.setString(3, model);
            pstmt.setInt(4, year);
            pstmt.setDouble(5, price);
            if (doors != null) { pstmt.setInt(6, doors); } else { pstmt.setNull(6, java.sql.Types.INTEGER); }
            if (ownerId != null) { pstmt.setInt(7, ownerId); } else { pstmt.setNull(7, java.sql.Types.INTEGER); }
            pstmt.executeUpdate();
            System.out.println(type + " inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicle";
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String type = rs.getString("type");
                Vehicle v;
                if ("CAR".equalsIgnoreCase(type)) {
                    Car car = new Car(
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getInt("id"),
                        null  // or fetch an Owner if available
                    );
                    v = car;
                } else if ("MOTORCYCLE".equalsIgnoreCase(type)) {
                    Motorcycle moto = new Motorcycle(
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        null  // or fetch an Owner if available
                    );
                    v = moto;
                } else {
                    throw new IllegalArgumentException("Unknown vehicle type: " + type);
                }
                vehicles.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public void updateVehicle(int id, String type, String brand, String model, int year, double price) {
        String sql = "UPDATE vehicle SET type = ?, brand = ?, model = ?, year = ?, price = ? WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, brand);
            pstmt.setString(3, model);
            pstmt.setInt(4, year);
            pstmt.setDouble(5, price);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println(type + " updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicle(int id) {
        String sql = "DELETE FROM vehicle WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Vehicle deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicleByDetails(String brand, String model, int year) {
        String sql = "DELETE FROM vehicle WHERE brand = ? AND model = ? AND year = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, brand);
            pstmt.setString(2, model);
            pstmt.setInt(3, year);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
