package model;

import model.Owner;

public interface Vehicle {
    String getBrand();
    String getModel();
    int getYear();
    double getPrice();
    int getId();
    void setId(int id);
    String getType();
    void setType(String type);
    Owner getOwner();
    void setOwner(Owner owner);
}