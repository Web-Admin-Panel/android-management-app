package com.example.rentalmanagementsystem.Entities;

import java.util.List;

public enum BillType {
    Electricity ("Electricity"),
    Water("Water"),
    Internet ("Internet");

    BillType(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public final String displayTitle ;
}

