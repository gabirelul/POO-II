package model;

import model.Person;

public class Owner extends Person {
    private String phone;
    private String email;

    public Owner(String name, String phone, String email) {
        super(name);
        this.phone = phone;
        this.email = email;
    }

    public Owner(int id, String name, String phone, String email) {
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
        return "Owner{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}