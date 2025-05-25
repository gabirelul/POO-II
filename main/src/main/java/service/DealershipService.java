package service;

import model.Dealership;
import model.Employee;
import model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class DealershipService {
    private List<Dealership> dealerships = new ArrayList<>();

    public void addDealership(Dealership d) {
        dealerships.add(d);
    }

    public List<Dealership> listDealerships() {
        return new ArrayList<>(dealerships);
    }

    public void addVehicleToDealership(Dealership d, Vehicle v) {
        d.addVehicle(v);
    }

    public void addEmployeeToDealership(Dealership d, Employee e) {
        d.addEmployee(e);
    }

    public List<Employee> getEmployees(Dealership d) {
        return new ArrayList<>(d.getEmployees());
    }

    public List<Vehicle> getVehicles(Dealership d) {
        return new ArrayList<>(d.getVehicles());
    }
}