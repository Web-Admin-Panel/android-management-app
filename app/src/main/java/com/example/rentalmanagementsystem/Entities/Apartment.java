package com.example.rentalmanagementsystem.Entities;

public class Apartment {
    private int id;
    private String address;
    private double rent;

    public Apartment(int id, String address, double rent) {
        this.id = id;
        this.address = address;
        this.rent = rent;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getRent() {
        return rent;
    }
}
