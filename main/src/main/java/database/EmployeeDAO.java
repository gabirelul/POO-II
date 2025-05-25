package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Employee;

public class EmployeeDAO {

    /** Creează tabela employee cu FK către dealership */
    public void createTable() {
        String sql = """
            PRAGMA foreign_keys = ON;
            CREATE TABLE IF NOT EXISTS employee (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                position TEXT,
                dealership_id INTEGER,
                FOREIGN KEY(dealership_id) REFERENCES dealership(id)
            );
        """;
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Inserează un angajat nou */
    public void insertEmployee(String name, String position, Integer dealershipId) {
        String sql = "INSERT INTO employee(name, position, dealership_id) VALUES (?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            if (dealershipId != null) {
                pstmt.setInt(3, dealershipId);
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Returnează toți angajații */
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee e = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getInt("dealership_id")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /** Actualizează datele unui angajat existent */
    public void updateEmployee(int id, String name, String position, Integer dealershipId) {
        String sql = "UPDATE employee SET name = ?, position = ?, dealership_id = ? WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            if (dealershipId != null) {
                pstmt.setInt(3, dealershipId);
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Șterge un angajat după ID */
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}