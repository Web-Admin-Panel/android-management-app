package com.example.rentalmanagementsystem.tests;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.rentalmanagementsystem.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestCustomers {

    private static final String[] NAMES = {"John Doe", "Jane Smith", "Alice Johnson", "Robert Brown", "Emily Davis"};
    private static final String[] PHONES = {"1234567890", "0987654321", "1112223333", "4445556666", "7778889999"};
    private static final String[] EMAILS = {"john@example.com", "jane@example.com", "alice@example.com", "robert@example.com", "emily@example.com"};

    public static List<ContentValues> generateTestCustomers() {
        List<ContentValues> customerList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 7; i++) {
            ContentValues values = new ContentValues();
            values.put("name", NAMES[random.nextInt(NAMES.length)]);
            values.put("phone", PHONES[random.nextInt(PHONES.length)]);
            values.put("email", EMAILS[random.nextInt(EMAILS.length)]);
            customerList.add(values);
        }

        return customerList;
    }

    public static void insertTestCustomers(DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContentValues> customers = generateTestCustomers();

        for (ContentValues values : customers) {
            db.insert("customers", null, values);
        }
    }
}
