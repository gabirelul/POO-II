package service;

import database.OwnerDAO;
import model.Owner;
import java.util.List;
import java.util.Scanner;

public class OwnerService {
    private final OwnerDAO ownerDAO;

    public OwnerService() {
        this.ownerDAO = new OwnerDAO();
    }

    public void addOwner(Scanner scanner) {
        System.out.println("\nAdauga proprietar nou:");
        System.out.print("Nume: ");
        String name = scanner.nextLine();
        System.out.print("Telefon: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        ownerDAO.insertOwner(name, phone, email);
        System.out.println("Proprietar adaugat cu succes!");
    }

    public void displayAllOwners() {
        System.out.println("\nLista proprietarilor:");
        List<Owner> owners = ownerDAO.getAllOwners();
        for (Owner owner : owners) {
            System.out.println("ID: " + owner.getId());
            System.out.println("Nume: " + owner.getName());
            System.out.println("Telefon: " + owner.getPhone());
            System.out.println("Email: " + owner.getEmail());
            System.out.println("---------------");
        }
    }

    public void updateOwner(Scanner scanner) {
        System.out.println("\nActualizeaza proprietar:");
        System.out.print("ID proprietar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nume nou: ");
        String name = scanner.nextLine();
        System.out.print("Telefon nou: ");
        String phone = scanner.nextLine();
        System.out.print("Email nou: ");
        String email = scanner.nextLine();

        ownerDAO.updateOwner(id, name, phone, email);
        System.out.println("Proprietar actualizat cu succes!");
    }

    public void deleteOwner(Scanner scanner) {
        System.out.println("\nSterge proprietar:");
        System.out.print("ID proprietar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        ownerDAO.deleteOwner(id);
        System.out.println("Proprietar sters cu succes!");
    }
} 