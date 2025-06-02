package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Dealership;
import service.AuditService;

public class DealershipDAO {
    private final AuditService auditService = AuditService.getInstance();

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS dealership (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL UNIQUE,
                location TEXT NOT NULL,
                contact_email TEXT NOT NULL UNIQUE
            );
        """;
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'dealership' created or already exists.");
            auditService.logAction("CREATE_DEALERSHIP_TABLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS dealership";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'dealership' dropped.");
            auditService.logAction("DROP_DEALERSHIP_TABLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDealership(String name, String location, String contactEmail) {
        String sql = "INSERT INTO dealership(name, location, contact_email) VALUES (?, ?, ?)";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setString(3, contactEmail);
            pstmt.executeUpdate();
            auditService.logAction("INSERT_DEALERSHIP");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Dealership> getAllDealerships() {
        List<Dealership> dealerships = new ArrayList<>();
        String sql = "SELECT * FROM dealership";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                dealerships.add(new Dealership(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dealerships;
    }

    public void updateDealership(int id, String name, String address, String phone, String email) {
        String sql = "UPDATE dealership SET name = ?, address = ?, phone = ?, email = ? WHERE id = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            System.out.println("Dealership updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDealership(int id) {
        String sql = "DELETE FROM dealership WHERE id = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Dealership deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDealership(String name, String address, String phone, String email) {
        String sql = "INSERT INTO dealership(name, address, phone, email) VALUES(?, ?, ?, ?)";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
            System.out.println("Dealership inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addVehicleToDealership(int dealershipId, int vehicleId) {
        String sql = "INSERT INTO dealership_vehicle(dealership_id, vehicle_id) VALUES(?, ?)";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dealershipId);
            pstmt.setInt(2, vehicleId);
            pstmt.executeUpdate();
            System.out.println("Vehicle added to dealership.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeVehicleFromDealership(int dealershipId, int vehicleId) {
        String sql = "DELETE FROM dealership_vehicle WHERE dealership_id = ? AND vehicle_id = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dealershipId);
            pstmt.setInt(2, vehicleId);
            pstmt.executeUpdate();
            System.out.println("Vehicle removed from dealership.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}