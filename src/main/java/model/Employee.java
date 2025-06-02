package model;

public class Employee extends Person {
    private String position;
    private int dealershipId;
    private String phone;
    private String email;

    // Constructor fără ID (la insert)
    public Employee(String name, String phone, String email, String position, int dealershipId) {
        super(name);
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.dealershipId = dealershipId;
    }

    // Constructor cu ID (la citire din DB)
    public Employee(int id, String name, String phone, String email, String position, int dealershipId) {
        super(id, name);
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.dealershipId = dealershipId;
    }

    // Getter și setter pentru position
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    // Getter și setter pentru dealershipId
    public int getDealershipId() {
        return dealershipId;
    }
    public void setDealershipId(int dealershipId) {
        this.dealershipId = dealershipId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", position='" + position + '\'' +
                ", dealershipId=" + dealershipId +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}