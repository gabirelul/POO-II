package model;

import model.Person;

public class Owner implements Person {
    private int id;
    private String name;
    private String phone;
    private String email;

    public Owner(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " - " + phone + " - " + email;
    }
}