package com.example.rentalmanagementsystem.tests;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.rentalmanagementsystem.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestApartments {

    private static final String[] ADDRESSES = {
            "123 Main St", "456 Elm St", "789 Maple Ave", "101 Oak Dr", "202 Pine Ln"
    };
    private static final double[] RENTS = {1200.00, 1500.50, 1800.75, 2000.00, 2200.25};

    public static List<ContentValues> generateTestApartments() {
        List<ContentValues> apartmentList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 7; i++) {
            ContentValues values = new ContentValues();
            values.put("address", ADDRESSES[random.nextInt(ADDRESSES.length)]);
            values.put("rent", RENTS[random.nextInt(RENTS.length)]);
            apartmentList.add(values);
        }

        return apartmentList;
    }

    public static void insertTestApartments(DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContentValues> apartments = generateTestApartments();

        for (ContentValues values : apartments) {
            db.insert("apartments", null, values);
        }
    }
}
