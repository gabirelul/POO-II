package model;

public abstract class AbstractVehicle implements Vehicle {
    protected String type;
    protected String brand;
    protected String model;
    protected int year;
    protected double price;
    protected Owner owner;
    protected int id;

    public AbstractVehicle(String type, String brand, String model, int year, double price, Owner owner) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.owner = owner;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public Owner getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Vehicle-specific behavior to start the engine.
     */
    public abstract void start();

    /**
     * Vehicle-specific behavior to stop the engine.
     */
    public abstract void stop();
}