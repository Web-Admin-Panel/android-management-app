package com.example.rentalmanagementsystem.Entities;

public class Employee {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String position;

    public Employee(int id, String name, String phone, String email, String position) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }
}
