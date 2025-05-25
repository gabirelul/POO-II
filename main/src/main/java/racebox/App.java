 package racebox;

import model.Car;
import model.Dealership;
import model.Employee;
import model.Motorcycle;
import model.Owner;
import model.Vehicle;
import service.DealershipService;
import service.VehicleService;
import database.VehicleDAO;
import database.OwnerDAO;
import database.DealershipDAO;
import database.EmployeeDAO;
import database.DealershipVehicleDAO;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        VehicleService vehicleService = new VehicleService();
        DealershipService dealershipService = new DealershipService();

        VehicleDAO vehicleDAO = new VehicleDAO();
        OwnerDAO ownerDAO = new OwnerDAO();
        DealershipDAO dealDAO = new DealershipDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        DealershipVehicleDAO dvDAO = new DealershipVehicleDAO();

        vehicleDAO.createTable();
        ownerDAO.createTable();
        dealDAO.createTable();
        empDAO.createTable();
        dvDAO.createTable();

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        Owner owner1 = new Owner("Ion Popescu", "0712345678", "ion.popescu@example.com");
        Owner owner2 = new Owner("Maria Ionescu", "0722333444", "maria.ionescu@example.com");

        Car car1 = new Car("Toyota", "Corolla", 2018, 15000, 4, owner1);
        Car car2 = new Car("Ford", "Focus", 2020, 18000, 4, owner2);
        Car car3 = new Car("Toyota", "Corolla", 2006, 18000, 4, owner2);
        Motorcycle moto1 = new Motorcycle("Yamaha", "MT-07", 2019, 7500, owner1);

        vehicleService.addVehicle(car1);
        vehicleService.addVehicle(car2);
        vehicleService.addVehicle(car3);
        vehicleService.addVehicle(moto1);

        Dealership dealership1 = new Dealership("AutoLux", "Str. Libertății 10");
        Dealership dealership2 = new Dealership("MotoPlus", "Bd. Victoriei 23");

        dealershipService.addDealership(dealership1);
        dealershipService.addDealership(dealership2);

        Employee emp1 = new Employee("Andrei Popa", "Manager", 0);
        Employee emp2 = new Employee("Elena Marin", "Vanzari", 0);

        dealershipService.addEmployeeToDealership(dealership1, emp1);
        dealershipService.addEmployeeToDealership(dealership1, emp2);

        dealershipService.addVehicleToDealership(dealership1, car1);
        dealershipService.addVehicleToDealership(dealership2, moto1);

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add vehicle");
            System.out.println("2. Show all vehicles");
            System.out.println("3. Filter by brand");
            System.out.println("4. Filter by price");
            System.out.println("5. Sort by year");
            System.out.println("6. Delete vehicle by brand, model and year");
            System.out.println("7. Create dealership");
            System.out.println("8. Add employee to dealership");
            System.out.println("9. Add vehicle to dealership");
            System.out.println("10. Show dealership employees");
            System.out.println("11. Show dealership vehicles");
            System.out.println("12. Add owner");
            System.out.println("13. List all owners");
            System.out.println("14. Update owner");
            System.out.println("15. Delete owner");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Type (car/motorcycle): ");
                    String type = scanner.nextLine();

                    System.out.print("Brand: ");
                    String brand = scanner.nextLine();

                    System.out.print("Model: ");
                    String model = scanner.nextLine();

                    System.out.print("Year: ");
                    int year = scanner.nextInt();

                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Owner name: ");
                    String ownerName = scanner.nextLine();

                    System.out.print("Owner phone: ");
                    String ownerPhone = scanner.nextLine();
                    System.out.print("Owner email: ");
                    String ownerEmail = scanner.nextLine();

                    Owner owner = new Owner(ownerName, ownerPhone, ownerEmail);

                    if (type.equalsIgnoreCase("car")) {
                        System.out.print("Number of doors: ");
                        int doors = scanner.nextInt();
                        scanner.nextLine();
                        vehicleService.addVehicle(new Car(brand, model, year, price, doors, owner));
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                    } else if (type.equalsIgnoreCase("motorcycle")) {
                        vehicleService.addVehicle(new Motorcycle(brand, model, year, price, owner));
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                    } else {
                        System.out.println("Unknown type.");
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                    }
                }
                case 2 -> {
                    System.out.println("=== ALL VEHICLES ===");
                    vehicleService.listVehicles().forEach(v ->
                            System.out.printf("%s %s | Year: %d | Price: $%.2f%n", v.getBrand(), v.getModel(), v.getYear(), v.getPrice()));
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 3 -> {
                    System.out.print("Enter brand: ");
                    String marca = scanner.nextLine();
                    vehicleService.filterByBrand(marca).forEach(v ->
                            System.out.printf("%s %s | Price: $%.2f%n", v.getBrand(), v.getModel(), v.getPrice()));
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 4 -> {
                    System.out.print("Enter minimum price: ");
                    double pret = scanner.nextDouble();
                    scanner.nextLine();
                    vehicleService.filterByPrice(pret).forEach(v ->
                            System.out.printf("%s %s | Price: $%.2f%n", v.getBrand(), v.getModel(), v.getPrice()));
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 5 -> {
                    System.out.println("=== SORT BY YEAR ===");
                    vehicleService.sortByYear().forEach(v ->
                            System.out.printf("Year: %d | %s %s | Price: $%.2f%n", v.getYear(), v.getBrand(), v.getModel(), v.getPrice()));
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 6 -> {
                    System.out.print("Brand of vehicle to delete: ");
                    String brandDel = scanner.nextLine();
                    System.out.print("Model of vehicle to delete: ");
                    String modelDel = scanner.nextLine();
                    System.out.print("Year of vehicle to delete: ");
                    int yearDel = scanner.nextInt();
                    scanner.nextLine();

                    boolean removed = vehicleService.deleteVehicleExact(brandDel, modelDel, yearDel);
                    if (removed) {
                        System.out.println("Vehicle successfully deleted.");
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                    } else {
                        System.out.println("Vehicle with the specified brand, model, and year not found.");
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                    }
                }
                case 7 -> {
                    System.out.print("Dealership name: ");
                    String dName = scanner.nextLine();
                    System.out.print("Dealership address: ");
                    String dAddress = scanner.nextLine();
                    Dealership d = new Dealership(dName, dAddress);
                    dealershipService.addDealership(d);
                    System.out.println("Dealership added.");
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 8 -> {
                    System.out.print("Dealership name: ");
                    String dName = scanner.nextLine();
                    Dealership d = dealershipService.listDealerships().stream()
                            .filter(deal -> deal.getName().equalsIgnoreCase(dName))
                            .findFirst().orElse(null);
                    if (d == null) {
                        System.out.println("Dealership not found.");
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("Employee name: ");
                    String eName = scanner.nextLine();
                    System.out.print("Employee position: ");
                    String ePosition = scanner.nextLine();
                    Employee e = new Employee(eName, ePosition, d.getId());
                    dealershipService.addEmployeeToDealership(d, e);
                    System.out.println("Employee added.");
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 9 -> {
                    System.out.print("Dealership name: ");
                    String dName = scanner.nextLine();
                    Dealership d = dealershipService.listDealerships().stream()
                            .filter(deal -> deal.getName().equalsIgnoreCase(dName))
                            .findFirst().orElse(null);
                    if (d == null) {
                        System.out.println("Dealership not found.");
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("Vehicle type (car/motorcycle): ");
                    String type = scanner.nextLine();
                    System.out.print("Brand: ");
                    String brand = scanner.nextLine();
                    System.out.print("Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Year: ");
                    int year = scanner.nextInt();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Owner name: ");
                    String ownerName = scanner.nextLine();
                    System.out.print("Owner phone: ");
                    String ownerPhone = scanner.nextLine();
                    System.out.print("Owner email: ");
                    String ownerEmail = scanner.nextLine();
                    Owner owner = new Owner(ownerName, ownerPhone, ownerEmail);
                    Vehicle v;
                    if (type.equalsIgnoreCase("car")) {
                        System.out.print("Number of doors: ");
                        int doors = scanner.nextInt();
                        scanner.nextLine();
                        v = new Car(brand, model, year, price, doors, owner);
                    } else if (type.equalsIgnoreCase("motorcycle")) {
                        v = new Motorcycle(brand, model, year, price, owner);
                    } else {
                        System.out.println("Unknown type.");
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                        break;
                    }
                    dealershipService.addVehicleToDealership(d, v);
                    System.out.println("Vehicle added to dealership.");
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 10 -> {
                    System.out.print("Dealership name: ");
                    String dName = scanner.nextLine();
                    Dealership d = dealershipService.listDealerships().stream()
                            .filter(deal -> deal.getName().equalsIgnoreCase(dName))
                            .findFirst().orElse(null);
                    if (d == null) {
                        System.out.println("Dealership not found.");
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.println("=== Dealership employees ===");
                    dealershipService.getEmployees(d).forEach(emp ->
                            System.out.printf("Name: %s | Position: %s | DealershipId: %d%n",
                                    emp.getName(), emp.getPosition(), emp.getDealershipId()));
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 11 -> {
                    System.out.print("Dealership name: ");
                    String dName = scanner.nextLine();
                    Dealership d = dealershipService.listDealerships().stream()
                            .filter(deal -> deal.getName().equalsIgnoreCase(dName))
                            .findFirst().orElse(null);
                    if (d == null) {
                        System.out.println("Dealership not found.");
                        System.out.println("Operation completed. Press Enter to return to the menu.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.println("=== Dealership vehicles ===");
                    dealershipService.getVehicles(d).forEach(vh ->
                            System.out.printf("%s %s | Year: %d | Price: $%.2f%n", vh.getBrand(), vh.getModel(), vh.getYear(), vh.getPrice()));
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 12 -> {
                    System.out.print("Owner name: ");
                    String oName = scanner.nextLine();
                    System.out.print("Owner phone: ");
                    String oPhone = scanner.nextLine();
                    System.out.print("Owner email: ");
                    String oEmail = scanner.nextLine();
                    ownerDAO.insertOwner(oName, oPhone, oEmail);
                    System.out.println("Owner added.");
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 13 -> {
                    System.out.println("=== ALL OWNERS ===");
                    ownerDAO.getAllOwners().forEach(o -> System.out.println(o));
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 14 -> {
                    System.out.print("Owner ID to update: ");
                    int uId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("New name: ");
                    String newName = scanner.nextLine();
                    System.out.print("New phone: ");
                    String newPhone = scanner.nextLine();
                    System.out.print("New email: ");
                    String newEmail = scanner.nextLine();
                    ownerDAO.updateOwner(uId, newName, newPhone, newEmail);
                    System.out.println("Owner updated.");
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 15 -> {
                    System.out.print("Owner ID to delete: ");
                    int dId = scanner.nextInt();
                    scanner.nextLine();
                    ownerDAO.deleteOwner(dId);
                    System.out.println("Owner deleted.");
                    System.out.println("Operation completed. Press Enter to return to the menu.");
                    scanner.nextLine();
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
