package model;

public class Motorcycle extends AbstractVehicle {

    public Motorcycle(String brand, String model, int year, double price, Owner owner) {
        super("MOTORCYCLE", brand, model, year, price, owner);
    }

    @Override
    public void start() {
        System.out.println("Motorcycle engine started.");
    }

    @Override
    public void stop() {
        System.out.println("Motorcycle engine stopped.");
    }
}