import service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VehicleService vehicleService = new VehicleService();
        DealershipService dealershipService = new DealershipService();
        EmployeeService employeeService = new EmployeeService();
        OwnerService ownerService = new OwnerService();

        while (true) {
            System.out.println("\nMeniu principal:");
            System.out.println("1. Adauga vehicul");
            System.out.println("2. Afiseaza toate vehiculele");
            System.out.println("3. Actualizeaza vehicul");
            System.out.println("4. Sterge vehicul");
            System.out.println("5. Adauga dealership");
            System.out.println("6. Afiseaza toate dealership-urile");
            System.out.println("7. Actualizeaza dealership");
            System.out.println("8. Sterge dealership");
            System.out.println("9. Adauga angajat");
            System.out.println("10. Afiseaza toti angajatii");
            System.out.println("11. Actualizeaza angajat");
            System.out.println("12. Sterge angajat");
            System.out.println("13. Adauga proprietar");
            System.out.println("14. Afiseaza toti proprietarii");
            System.out.println("15. Actualizeaza proprietar");
            System.out.println("16. Sterge proprietar");
            System.out.println("17. Reseteaza si initializeaza baza de date");
            System.out.println("18. Adauga vehicul la dealership");
            System.out.println("19. Sterge vehicul de la dealership");
            System.out.println("0. Iesire");
            System.out.print("\nAlege o optiune: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    vehicleService.addVehicle(scanner);
                    break;
                case 2:
                    vehicleService.displayAllVehicles();
                    break;
                case 3:
                    vehicleService.updateVehicle(scanner);
                    break;
                case 4:
                    vehicleService.deleteVehicle(scanner);
                    break;
                case 5:
                    dealershipService.addDealership(scanner);
                    break;
                case 6:
                    dealershipService.displayAllDealerships();
                    break;
                case 7:
                    dealershipService.updateDealership(scanner);
                    break;
                case 8:
                    dealershipService.deleteDealership(scanner);
                    break;
                case 9:
                    employeeService.addEmployee(scanner);
                    break;
                case 10:
                    employeeService.displayAllEmployees();
                    break;
                case 11:
                    employeeService.updateEmployee(scanner);
                    break;
                case 12:
                    employeeService.deleteEmployee(scanner);
                    break;
                case 13:
                    ownerService.addOwner(scanner);
                    break;
                case 14:
                    ownerService.displayAllOwners();
                    break;
                case 15:
                    ownerService.updateOwner(scanner);
                    break;
                case 16:
                    ownerService.deleteOwner(scanner);
                    break;
                case 17:
                    database.DataInitializer.initializeDatabase();
                    break;
                case 18:
                    dealershipService.addVehicleToDealership(scanner);
                    break;
                case 19:
                    dealershipService.removeVehicleFromDealership(scanner);
                    break;
                case 0:
                    System.out.println("La revedere!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Optiune invalida!");
            }
        }
    }
} 