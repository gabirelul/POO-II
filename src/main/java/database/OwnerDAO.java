package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Owner;
import service.AuditService;

public class OwnerDAO {
    private final AuditService auditService = AuditService.getInstance();

    /** Creează tabela owner */
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS owner (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "email TEXT NOT NULL" +
                    ")";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Owner table created.");
            auditService.logAction("CREATE_OWNER_TABLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Șterge tabela owner dacă există */
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS owner";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Owner table dropped.");
            auditService.logAction("DROP_OWNER_TABLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Inserează un proprietar nou */
    public void insertOwner(String name, String phone, String email) {
        String sql = "INSERT INTO owner(name, phone, email) VALUES(?, ?, ?)";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println("Owner inserted.");
            auditService.logAction("INSERT_OWNER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Returnează toți proprietarii */
    public List<Owner> getAllOwners() {
        List<Owner> owners = new ArrayList<>();
        String sql = "SELECT * FROM owner";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                owners.add(new Owner(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email")
                ));
            }
            auditService.logAction("GET_ALL_OWNERS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return owners;
    }

    /** Actualizează un proprietar existent */
    public void updateOwner(int id, String name, String phone, String email) {
        String sql = "UPDATE owner SET name = ?, phone = ?, email = ? WHERE id = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Owner updated.");
            auditService.logAction("UPDATE_OWNER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Șterge un proprietar după ID */
    public void deleteOwner(int id) {
        String sql = "DELETE FROM owner WHERE id = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Owner deleted.");
            auditService.logAction("DELETE_OWNER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Verifică dacă tabela owner există deja */
    public boolean tableExists() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='owner'";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}