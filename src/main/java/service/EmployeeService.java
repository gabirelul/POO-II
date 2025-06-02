package service;

import database.EmployeeDAO;
import model.Employee;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    public void addEmployee(Scanner scanner) {
        System.out.println("\nAdauga angajat nou:");
        System.out.print("Nume: ");
        String name = scanner.nextLine();
        System.out.print("Telefon: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Pozitie: ");
        String position = scanner.nextLine();
        System.out.print("ID Dealership: ");
        int dealershipId = scanner.nextInt();
        scanner.nextLine();

        employeeDAO.insertEmployee(name, phone, email, position, dealershipId);
        System.out.println("Angajat adaugat cu succes!");
    }

    public void displayAllEmployees() {
        System.out.println("\nLista angajatilor:");
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getId());
            System.out.println("Nume: " + employee.getName());
            System.out.println("Telefon: " + employee.getPhone());
            System.out.println("Email: " + employee.getEmail());
            System.out.println("Pozitie: " + employee.getPosition());
            System.out.println("ID Dealership: " + employee.getDealershipId());
            System.out.println("---------------");
        }
    }

    public void updateEmployee(Scanner scanner) {
        System.out.println("\nActualizeaza angajat:");
        System.out.print("ID angajat: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nume nou: ");
        String name = scanner.nextLine();
        System.out.print("Telefon nou: ");
        String phone = scanner.nextLine();
        System.out.print("Email nou: ");
        String email = scanner.nextLine();
        System.out.print("Pozitie noua: ");
        String position = scanner.nextLine();
        System.out.print("ID Dealership nou: ");
        int dealershipId = scanner.nextInt();
        scanner.nextLine();

        employeeDAO.updateEmployee(id, name, phone, email, position, dealershipId);
        System.out.println("Angajat actualizat cu succes!");
    }

    public void deleteEmployee(Scanner scanner) {
        System.out.println("\nSterge angajat:");
        System.out.print("ID angajat: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        employeeDAO.deleteEmployee(id);
        System.out.println("Angajat sters cu succes!");
    }
} 