package database;

import java.sql.Connection;
import java.sql.Statement;

public class DataInitializer {
    public static void initializeDatabase() {
        dropTables();
        createTables();
        insertSampleData();
    }

    private static void dropTables() {
        String[] dropTableSQL = {
            "DROP TABLE IF EXISTS dealership_vehicle",
            "DROP TABLE IF EXISTS employee",
            "DROP TABLE IF EXISTS vehicle",
            "DROP TABLE IF EXISTS dealership",
            "DROP TABLE IF EXISTS owner"
        };

        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            for (String sql : dropTableSQL) {
                stmt.execute(sql);
            }
            // Ștergem și secvențele AUTOINCREMENT
            stmt.execute("DELETE FROM sqlite_sequence");
            System.out.println("Toate tabelele au fost sterse.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTables() {
        String[] createTableSQL = {
            "CREATE TABLE IF NOT EXISTS dealership (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "address TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "email TEXT NOT NULL" +
            ")",

            "CREATE TABLE IF NOT EXISTS vehicle (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT NOT NULL," +
                "brand TEXT NOT NULL," +
                "model TEXT NOT NULL," +
                "year INTEGER NOT NULL," +
                "price REAL NOT NULL," +
                "doors INTEGER," +
                "owner_id INTEGER," +
                "FOREIGN KEY (owner_id) REFERENCES owner(id)" +
            ")",

            "CREATE TABLE IF NOT EXISTS owner (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "email TEXT NOT NULL" +
            ")",

            "CREATE TABLE IF NOT EXISTS employee (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "position TEXT NOT NULL," +
                "dealership_id INTEGER," +
                "FOREIGN KEY (dealership_id) REFERENCES dealership(id)" +
            ")",

            "CREATE TABLE IF NOT EXISTS dealership_vehicle (" +
                "dealership_id INTEGER," +
                "vehicle_id INTEGER," +
                "PRIMARY KEY (dealership_id, vehicle_id)," +
                "FOREIGN KEY (dealership_id) REFERENCES dealership(id)," +
                "FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)" +
            ")"
        };

        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            for (String sql : createTableSQL) {
                stmt.execute(sql);
            }
            System.out.println("Tabelele au fost create cu succes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertSampleData() {
        String[] sampleDataSQL = {
            "INSERT INTO dealership (name, address, phone, email) VALUES " +
            "('Racebox Bucuresti', 'Str. Victoriei 100, Bucuresti', '0722111222', 'contact@raceboxbucuresti.ro')," +
            "('Topaz Romania', 'Bd. Timisoara 50, Bucuresti', '0733222333', 'office@topazromania.ro')," +
            "('GC Auto', 'Str. Libertatii 25, Cluj-Napoca', '0744333444', 'contact@gcauto.ro')",

            "INSERT INTO owner (name, phone, email) VALUES " +
            "('Alexandru Munteanu', '0722555666', 'alex.munteanu@email.com')," +
            "('Elena Dumitrescu', '0733666777', 'elena.dumitrescu@email.com')," +
            "('Andrei Rusu', '0744777888', 'andrei.rusu@email.com')",

            "INSERT INTO vehicle (type, brand, model, year, price, doors, owner_id) VALUES " +
            "('CAR', 'Dacia', 'Logan', 2020, 12000, 4, 1)," +
            "('CAR', 'Volkswagen', 'Golf', 2019, 15000, 4, 2)," +
            "('MOTORCYCLE', 'Honda', 'CBR', 2021, 9000, NULL, 3)," +
            "('CAR', 'Ford', 'Focus', 2018, 13000, 4, 1)," +
            "('MOTORCYCLE', 'Yamaha', 'MT-07', 2020, 8500, NULL, 2)",

            "INSERT INTO employee (name, phone, email, position, dealership_id) VALUES " +
            "('Cristina Popov', '0755888999', 'cristina.popov@email.com', 'Manager', 1)," +
            "('Mihai Stancu', '0766999000', 'mihai.stancu@email.com', 'Sales', 2)",

            "INSERT INTO dealership_vehicle (dealership_id, vehicle_id) VALUES " +
            "(1, 1), (1, 3), (2, 2), (2, 4), (3, 5)"
        };

        Connection conn = DBconnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            for (String sql : sampleDataSQL) {
                stmt.execute(sql);
            }
            System.out.println("Datele de test au fost inserate cu succes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 