package service;

import model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class VehicleService {
    private List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public List<Vehicle> listVehicles() {
        return List.copyOf(vehicles);
    }

    public List<Vehicle> filterByBrand(String brand) {
        return vehicles.stream()
                .filter(v -> v.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    public List<Vehicle> filterByPrice(double minPrice) {
        return vehicles.stream()
                .filter(v -> v.getPrice() >= minPrice)
                .collect(Collectors.toList());
    }

    public List<Vehicle> sortByYear() {
        return vehicles.stream()
                .sorted(Comparator.comparingInt(Vehicle::getYear))
                .collect(Collectors.toList());
    }

    public boolean deleteVehicleExact(String brand, String model, int year) {
        return vehicles.removeIf(v ->
                v.getBrand().equalsIgnoreCase(brand) &&
                v.getModel().equalsIgnoreCase(model) &&
                v.getYear() == year
        );
    }
}
