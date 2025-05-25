package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Owner;

public class OwnerDAO {

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS owner (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                phone TEXT,
                email TEXT
            );
        """;
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOwner(String name, String phone, String email) {
        String sql = "INSERT INTO owner(name, phone, email) VALUES (?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Owner> getAllOwners() {
        List<Owner> list = new ArrayList<>();
        String sql = "SELECT * FROM owner";
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Owner o = new Owner(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
                o.setId(rs.getInt("id"));
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateOwner(int id, String name, String phone, String email) {
        String sql = "UPDATE owner SET name = ?, phone = ?, email = ? WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOwner(int id) {
        String sql = "DELETE FROM owner WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
