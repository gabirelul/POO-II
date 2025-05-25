package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dealership {
    private int id;
    private String contactEmail;
    private String name;
    private String address;
    private List<Vehicle> vehicles = new ArrayList<>();
    private Set<Employee> employees = new HashSet<>();

    public Dealership(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return "Dealership{id=" + id + ", name='" + name + '\'' +
               ", address='" + address + '\'' +
               ", contactEmail='" + contactEmail + '\'' +
               ", Vehicles=" + vehicles.size() +
               ", Employees=" + employees.size() +
               '}';
    }
}