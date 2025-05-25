package model;

public class Employee {
    private int id;
    private String name;
    private String position;
    private int dealershipId;

    public Employee(String name, String position, int dealershipId) {
        this.name = name;
        this.position = position;
        this.dealershipId = dealershipId;
    }

    public Employee(int id, String name, String position, int dealershipId) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.dealershipId = dealershipId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public int getDealershipId() {
        return dealershipId;
    }
    public void setDealershipId(int dealershipId) {
        this.dealershipId = dealershipId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", dealershipId=" + dealershipId +
                '}';
    }
}
