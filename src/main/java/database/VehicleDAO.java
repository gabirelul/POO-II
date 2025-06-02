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
import service.AuditService;

public class VehicleDAO {
    private final AuditService auditService = AuditService.getInstance();

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS vehicle (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                type TEXT NOT NULL, 
                brand TEXT NOT NULL,
                model TEXT NOT NULL,
                year INTEGER NOT NULL,
                price REAL NOT NULL,
                doors INTEGER,
                owner_id INTEGER NOT NULL,
                FOREIGN KEY(owner_id) REFERENCES owner(id) ON DELETE CASCADE
            );
        """;
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabelul 'vehicle' a fost creat sau exista deja.");
            auditService.logAction("CREATE_VEHICLE_TABLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS vehicle";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabelul 'vehicle' a fost sters.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertVehicle(String type, String brand, String model, int year, double price, Integer doors, Integer ownerId) {
        String sql = "INSERT INTO vehicle(type, brand, model, year, price, doors, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, brand);
            pstmt.setString(3, model);
            pstmt.setInt(4, year);
            pstmt.setDouble(5, price);
            if (doors != null) { pstmt.setInt(6, doors); } else { pstmt.setNull(6, java.sql.Types.INTEGER); }
            if (ownerId != null) { pstmt.setInt(7, ownerId); } else { pstmt.setNull(7, java.sql.Types.INTEGER); }
            pstmt.executeUpdate();
            System.out.println(type + " inserted.");
            auditService.logAction("INSERT_VEHICLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicle";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement();
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
                        rs.getInt("doors"),
                        null  // or fetch an Owner if available
                    );
                    car.setId(rs.getInt("id"));
                    v = car;
                } else if ("MOTORCYCLE".equalsIgnoreCase(type)) {
                    Motorcycle moto = new Motorcycle(
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        null  // or fetch an Owner if available
                    );
                    moto.setId(rs.getInt("id"));
                    v = moto;
                } else {
                    throw new IllegalArgumentException("Unknown vehicle type: " + type);
                }
                vehicles.add(v);
            }
            auditService.logAction("GET_ALL_VEHICLES");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public void updateVehicle(int id, String type, String brand, String model, int year, double price, Integer doors) {
        String sql = "UPDATE vehicle SET type = ?, brand = ?, model = ?, year = ?, price = ?, doors = ? WHERE id = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, brand);
            pstmt.setString(3, model);
            pstmt.setInt(4, year);
            pstmt.setDouble(5, price);
            if (doors != null) {
                pstmt.setInt(6, doors);
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
            System.out.println(type + " updated.");
            auditService.logAction("UPDATE_VEHICLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicle(int id) {
        Connection conn = DBconnection.getConnection();
        try {
            // Începem o tranzacție
            conn.setAutoCommit(false);
            
            // Ștergem vehiculul
            String deleteSql = "DELETE FROM vehicle WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            // Actualizăm ID-urile pentru toate înregistrările după cea ștearsă
            String updateSql = "UPDATE vehicle SET id = id - 1 WHERE id > ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            // Resetăm secvența AUTOINCREMENT
            String resetSql = "UPDATE sqlite_sequence SET seq = (SELECT MAX(id) FROM vehicle) WHERE name = 'vehicle'";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(resetSql);
            }

            // Comitem tranzacția
            conn.commit();
            conn.setAutoCommit(true);

            System.out.println("Vehicul sters si ID-uri reordonate.");
            auditService.logAction("DELETE_VEHICLE");
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void deleteVehicleByDetails(String brand, String model, int year) {
        String sql = "DELETE FROM vehicle WHERE brand = ? AND model = ? AND year = ?";
        Connection conn = DBconnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, brand);
            pstmt.setString(2, model);
            pstmt.setInt(3, year);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean tableExists() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='vehicle'";
        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
