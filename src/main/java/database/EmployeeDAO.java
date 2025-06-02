package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Employee;
import service.AuditService;

public class EmployeeDAO {
    private final AuditService auditService = AuditService.getInstance();

    /** Creează tabela employee cu FK către dealership */
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employee (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "position TEXT NOT NULL," +
                    "dealership_id INTEGER," +
                    "FOREIGN KEY (dealership_id) REFERENCES dealership(id)" +
                    ")";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabelul 'employee' a fost creat.");
            auditService.logAction("CREATE_EMPLOYEE_TABLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Inserează un angajat nou */
    public void insertEmployee(String name, String phone, String email, String position, Integer dealershipId) {
        String sql = "INSERT INTO employee(name, phone, email, position, dealership_id) VALUES(?, ?, ?, ?, ?)";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, position);
            if (dealershipId != null) {
                pstmt.setInt(5, dealershipId);
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            pstmt.executeUpdate();
            auditService.logAction("INSERT_EMPLOYEE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Returnează toți angajații */
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                employees.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("position"),
                    rs.getInt("dealership_id")
                ));
            }
            auditService.logAction("GET_ALL_EMPLOYEES");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    /** Actualizează datele unui angajat existent */
    public void updateEmployee(int id, String name, String phone, String email, String position, int dealershipId) {
        String sql = "UPDATE employee SET name = ?, phone = ?, email = ?, position = ?, dealership_id = ? WHERE id = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, position);
            pstmt.setInt(5, dealershipId);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            auditService.logAction("UPDATE_EMPLOYEE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Șterge un angajat după ID */
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            auditService.logAction("DELETE_EMPLOYEE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Șterge tabela employee */
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS employee";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabelul 'employee' a fost sters.");
            auditService.logAction("DROP_EMPLOYEE_TABLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Verifică dacă tabela employee există */
    public boolean tableExists() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='employee'";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}