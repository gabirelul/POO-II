package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Vehicle;
import database.VehicleDAO;
import model.Car;
import model.Motorcycle;

public class DealershipVehicleDAO {

    public void createTable() {
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
            
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS dealership_vehicle (
                    dealership_id INTEGER NOT NULL,
                    vehicle_id INTEGER NOT NULL,
                    PRIMARY KEY(dealership_id, vehicle_id),
                    FOREIGN KEY(dealership_id) REFERENCES dealership(id) ON DELETE CASCADE,
                    FOREIGN KEY(vehicle_id) REFERENCES vehicle(id) ON DELETE CASCADE
                );
            """;
            stmt.execute(createTableSQL);
            System.out.println("Table 'dealership_vehicle' created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS dealership_vehicle";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'dealership_vehicle' dropped.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void linkVehicleToDealership(int dealershipId, int vehicleId) {
        String sql = "INSERT INTO dealership_vehicle(dealership_id, vehicle_id) VALUES (?, ?)";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dealershipId);
            pstmt.setInt(2, vehicleId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vehicle> getVehiclesForDealership(int dealershipId) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = """
            SELECT v.* FROM vehicle v
            JOIN dealership_vehicle dv ON v.id = dv.vehicle_id
            WHERE dv.dealership_id = ?
        """;
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dealershipId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                if ("CAR".equalsIgnoreCase(type)) {
                    Car car = new Car(
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getInt("doors"),
                        null
                    );
                    car.setId(rs.getInt("id"));
                    vehicles.add(car);
                } else if ("MOTORCYCLE".equalsIgnoreCase(type)) {
                    Motorcycle moto = new Motorcycle(
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        null
                    );
                    moto.setId(rs.getInt("id"));
                    vehicles.add(moto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public void unlinkVehicleFromDealership(int dealershipId, int vehicleId) {
        String sql = "DELETE FROM dealership_vehicle WHERE dealership_id = ? AND vehicle_id = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dealershipId);
            pstmt.setInt(2, vehicleId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean tableExists() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='dealership_vehicle'";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}