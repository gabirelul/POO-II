package model;

public class Client extends Person {
    private String phone;
    private String email;

    public Client(String name, String phone, String email) {
        super(name);
        this.phone = phone;
        this.email = email;
    }

    public Client(int id, String name, String phone, String email) {
        super(id, name);
        this.phone = phone;
        this.email = email;
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
        return "Client{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
