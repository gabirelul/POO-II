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

public class DealershipVehicleDAO {

    public void createTable() {
        String sql = """
            PRAGMA foreign_keys = ON;
            CREATE TABLE IF NOT EXISTS dealership_vehicle (
                dealership_id INTEGER,
                vehicle_id    INTEGER,
                PRIMARY KEY(dealership_id, vehicle_id),
                FOREIGN KEY(dealership_id) REFERENCES dealership(id),
                FOREIGN KEY(vehicle_id)    REFERENCES vehicle(id)
            );
        """;
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void linkVehicleToDealership(int dealershipId, int vehicleId) {
        String sql = "INSERT INTO dealership_vehicle(dealership_id, vehicle_id) VALUES (?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dealershipId);
            pstmt.setInt(2, vehicleId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vehicle> getVehiclesForDealership(int dealershipId) {
        List<Vehicle> list = new ArrayList<>();
        String sql = """
            SELECT v.id FROM vehicle v
            JOIN dealership_vehicle dv ON v.id = dv.vehicle_id
            WHERE dv.dealership_id = ?
        """;
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dealershipId);
            try (ResultSet rs = pstmt.executeQuery()) {
                VehicleDAO vehicleDAO = new VehicleDAO();
                List<Vehicle> all = vehicleDAO.getAllVehicles();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    all.stream()
                       .filter(v -> v.getId() == id)
                       .findFirst()
                       .ifPresent(list::add);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void unlinkVehicleFromDealership(int dealershipId, int vehicleId) {
        String sql = "DELETE FROM dealership_vehicle WHERE dealership_id = ? AND vehicle_id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dealershipId);
            pstmt.setInt(2, vehicleId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}