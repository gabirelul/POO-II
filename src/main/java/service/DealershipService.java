package service;

import database.DealershipDAO;
import model.Dealership;
import java.util.List;
import java.util.Scanner;

public class DealershipService {
    private final DealershipDAO dealershipDAO;

    public DealershipService() {
        this.dealershipDAO = new DealershipDAO();
    }

    public void addDealership(Scanner scanner) {
        System.out.println("\nAdauga dealership nou:");
        System.out.print("Nume: ");
        String name = scanner.nextLine();
        System.out.print("Adresa: ");
        String address = scanner.nextLine();
        System.out.print("Telefon: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        dealershipDAO.insertDealership(name, address, phone, email);
        System.out.println("Dealership adaugat cu succes!");
    }

    public void displayAllDealerships() {
        System.out.println("\nLista dealership-urilor:");
        List<Dealership> dealerships = dealershipDAO.getAllDealerships();
        for (Dealership dealership : dealerships) {
            System.out.println("ID: " + dealership.getId());
            System.out.println("Nume: " + dealership.getName());
            System.out.println("Adresa: " + dealership.getAddress());
            System.out.println("Telefon: " + dealership.getPhone());
            System.out.println("Email: " + dealership.getEmail());
            System.out.println("---------------");
        }
    }

    public void updateDealership(Scanner scanner) {
        System.out.println("\nActualizeaza dealership:");
        System.out.print("ID dealership: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nume nou: ");
        String name = scanner.nextLine();
        System.out.print("Adresa noua: ");
        String address = scanner.nextLine();
        System.out.print("Telefon nou: ");
        String phone = scanner.nextLine();
        System.out.print("Email nou: ");
        String email = scanner.nextLine();

        dealershipDAO.updateDealership(id, name, address, phone, email);
        System.out.println("Dealership actualizat cu succes!");
    }

    public void deleteDealership(Scanner scanner) {
        System.out.println("\nSterge dealership:");
        System.out.print("ID dealership: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        dealershipDAO.deleteDealership(id);
        System.out.println("Dealership sters cu succes!");
    }

    public void addVehicleToDealership(Scanner scanner) {
        System.out.println("\nAdauga vehicul la dealership:");
        System.out.print("ID dealership: ");
        int dealershipId = scanner.nextInt();
        System.out.print("ID vehicul: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        dealershipDAO.addVehicleToDealership(dealershipId, vehicleId);
        System.out.println("Vehicul adaugat la dealership cu succes!");
    }

    public void removeVehicleFromDealership(Scanner scanner) {
        System.out.println("\nSterge vehicul de la dealership:");
        System.out.print("ID dealership: ");
        int dealershipId = scanner.nextInt();
        System.out.print("ID vehicul: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        dealershipDAO.removeVehicleFromDealership(dealershipId, vehicleId);
        System.out.println("Vehicul sters de la dealership cu succes!");
    }
}