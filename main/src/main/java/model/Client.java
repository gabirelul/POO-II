package model;

public class Client implements Person {
    private String name;
    private String phone;

    public Client(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return name + " - " + phone;
    }
}
