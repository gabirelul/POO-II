package service;

import database.VehicleDAO;
import model.Vehicle;
import model.Car;
import model.Motorcycle;
import java.util.List;
import java.util.Scanner;

public class VehicleService {
    private final VehicleDAO vehicleDAO;

    public VehicleService() {
        this.vehicleDAO = new VehicleDAO();
    }

    public void addVehicle(Scanner scanner) {
        System.out.println("\nAdauga vehicul nou:");
        System.out.print("Tip (c pentru masina, m pentru motocicleta): ");
        String type = scanner.nextLine().toLowerCase();
        
        System.out.print("Marca: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("An: ");
        int year = scanner.nextInt();
        System.out.print("Pret: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("ID Proprietar: ");
        int ownerId = scanner.nextInt();
        scanner.nextLine();

        if (type.equals("c")) {
            System.out.print("Numar de usi: ");
            int doors = scanner.nextInt();
            scanner.nextLine();
            vehicleDAO.insertVehicle("CAR", brand, model, year, price, doors, ownerId);
        } else if (type.equals("m")) {
            vehicleDAO.insertVehicle("MOTORCYCLE", brand, model, year, price, null, ownerId);
        }
        System.out.println("Vehicul adaugat cu succes!");
    }

    public void displayAllVehicles() {
        System.out.println("\nLista vehiculelor:");
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        for (Vehicle vehicle : vehicles) {
            System.out.println("ID: " + vehicle.getId());
            System.out.println("Tip: " + (vehicle instanceof Car ? "Masina" : "Motocicleta"));
            System.out.println("Marca: " + vehicle.getBrand());
            System.out.println("Model: " + vehicle.getModel());
            System.out.println("An: " + vehicle.getYear());
            System.out.println("Pret: " + vehicle.getPrice());
            if (vehicle instanceof Car) {
                System.out.println("Numar usi: " + ((Car) vehicle).getDoors());
            }
            System.out.println("---------------");
        }
    }

    public void updateVehicle(Scanner scanner) {
        System.out.println("\nActualizeaza vehicul:");
        System.out.print("ID vehicul: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tip nou (c pentru masina, m pentru motocicleta): ");
        String type = scanner.nextLine().toLowerCase();
        
        System.out.print("Marca noua: ");
        String brand = scanner.nextLine();
        System.out.print("Model nou: ");
        String model = scanner.nextLine();
        System.out.print("An nou: ");
        int year = scanner.nextInt();
        System.out.print("Pret nou: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        if (type.equals("c")) {
            System.out.print("Numar de usi nou: ");
            int doors = scanner.nextInt();
            scanner.nextLine();
            vehicleDAO.updateVehicle(id, "CAR", brand, model, year, price, doors);
        } else if (type.equals("m")) {
            vehicleDAO.updateVehicle(id, "MOTORCYCLE", brand, model, year, price, null);
        }
        System.out.println("Vehicul actualizat cu succes!");
    }

    public void deleteVehicle(Scanner scanner) {
        System.out.println("\nSterge vehicul:");
        System.out.print("ID vehicul: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        vehicleDAO.deleteVehicle(id);
        System.out.println("Vehicul sters cu succes!");
    }
}