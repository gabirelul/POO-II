package service;

import database.*;
import model.*;

public class DataInitializationService {
    private final OwnerDAO ownerDAO;
    private final VehicleDAO vehicleDAO;
    private final DealershipDAO dealershipDAO;
    private final EmployeeDAO employeeDAO;

    public DataInitializationService() {
        this.ownerDAO = new OwnerDAO();
        this.vehicleDAO = new VehicleDAO();
        this.dealershipDAO = new DealershipDAO();
        this.employeeDAO = new EmployeeDAO();
    }

    public void initializeTestData() {
        // Insert owners
        ownerDAO.insertOwner("Alex Gabriel", "0722123456", "alex.gabriel@email.com");
        ownerDAO.insertOwner("Gabriel Alexandru", "0733234567", "gabriel.alexandru@email.com");
        ownerDAO.insertOwner("Alex Chera", "0744345678", "alex.chera@email.com");

        // Insert dealerships
        dealershipDAO.insertDealership("Racebox Bucuresti", "Soseaua Colentina 450", "0721111222", "racebox@dealer.com");
        dealershipDAO.insertDealership("Topaz Romania", "Calea Turzii 100", "0722222333", "topaz@dealer.com");
        dealershipDAO.insertDealership("GC Auto", "Bulevardul Take Ionescu 50", "0723333444", "gcauto@dealer.com");

        // Insert vehicles
        vehicleDAO.insertVehicle("CAR", "Dacia", "Logan", 2020, 12000.0, 4, 1);
        vehicleDAO.insertVehicle("CAR", "Volkswagen", "Golf", 2019, 15000.0, 4, 2);
        vehicleDAO.insertVehicle("MOTORCYCLE", "Honda", "CBR", 2021, 8000.0, null, 3);
        vehicleDAO.insertVehicle("CAR", "Ford", "Focus", 2018, 13500.0, 4, 1);
        vehicleDAO.insertVehicle("MOTORCYCLE", "Yamaha", "MT-07", 2020, 7500.0, null, 2);

        // Insert employees
        employeeDAO.insertEmployee("Alexandru Munteanu", "0724444555", "alex.munteanu@email.com", "Sales Manager", 1);
        employeeDAO.insertEmployee("Elena Dumitrescu", "0725555666", "elena.dumitrescu@email.com", "Sales Representative", 1);
        employeeDAO.insertEmployee("Andrei Rusu", "0726666777", "andrei.rusu@email.com", "Service Manager", 2);
        employeeDAO.insertEmployee("Cristina Popov", "0727777888", "cristina.popov@email.com", "Sales Representative", 2);
        employeeDAO.insertEmployee("Mihai Stancu", "0728888999", "mihai.stancu@email.com", "Service Technician", 3);

        // Link vehicles to dealerships
        dealershipDAO.addVehicleToDealership(1, 1);
        dealershipDAO.addVehicleToDealership(1, 4);
        dealershipDAO.addVehicleToDealership(2, 2);
        dealershipDAO.addVehicleToDealership(2, 5);
        dealershipDAO.addVehicleToDealership(3, 3);

        System.out.println("Datele de test au fost initializate cu succes!");
    }
} 