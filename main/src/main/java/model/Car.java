package model;

public class Car extends AbstractVehicle {
    private int doors;

    public Car(String brand, String model, int year, double price, int doors, Owner owner) {
        super("CAR", brand, model, year, price, owner);
        this.doors = doors;
      }

    public int getDoors() {
        return doors;
    }

    @Override
    public Owner getOwner() {
        return super.getOwner();
    }

    @Override
    public void start() {
        System.out.println("Car engine started.");
    }

    @Override
    public void stop() {
        System.out.println("Car engine stopped.");
    }
}