package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Dealership;

public class DealershipDAO {

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS dealership (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                location TEXT,
                contact_email TEXT
            );
        """;
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDealership(String name, String location, String contactEmail) {
        String sql = "INSERT INTO dealership(name, location, contact_email) VALUES (?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setString(3, contactEmail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Dealership> getAllDealerships() {
        List<Dealership> list = new ArrayList<>();
        String sql = "SELECT * FROM dealership";
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Dealership d = new Dealership(
                        rs.getString("name"),
                        rs.getString("location")
                );
                d.setId(rs.getInt("id"));
                d.setContactEmail(rs.getString("contact_email"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateDealership(int id, String name, String location, String contactEmail) {
        String sql = "UPDATE dealership SET name = ?, location = ?, contact_email = ? WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setString(3, contactEmail);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDealership(int id) {
        String sql = "DELETE FROM dealership WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}