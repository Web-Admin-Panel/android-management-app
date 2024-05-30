package com.example.rentalmanagementsystem.Entities;

public class Admin {
    private int id;
    private String username;
    private String password;

    private int level;
    public Admin(int id, String username, String password, int level) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLevel() {
        return level;
    }
}
