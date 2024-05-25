package com.example.rentalmanagementsystem.tests;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.rentalmanagementsystem.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestEmployees {

    private static final String[] NAMES = {"Michael Scott", "Jim Halpert", "Pam Beesly", "Dwight Schrute", "Stanley Hudson"};
    private static final String[] PHONES = {"5551234567", "5559876543", "5551112222", "5553334444", "5555556666"};
    private static final String[] EMAILS = {"michael@dundermifflin.com", "jim@dundermifflin.com", "pam@dundermifflin.com", "dwight@dundermifflin.com", "stanley@dundermifflin.com"};
    private static final String[] POSITIONS = {"Manager", "Salesman", "Receptionist", "Assistant to the Regional Manager", "Salesman"};

    public static List<ContentValues> generateTestEmployees() {
        List<ContentValues> employeeList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 7; i++) {
            ContentValues values = new ContentValues();
            values.put("name", NAMES[random.nextInt(NAMES.length)]);
            values.put("phone", PHONES[random.nextInt(PHONES.length)]);
            values.put("email", EMAILS[random.nextInt(EMAILS.length)]);
            values.put("position", POSITIONS[random.nextInt(POSITIONS.length)]);
            employeeList.add(values);
        }

        return employeeList;
    }

    public static void insertTestEmployees(DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContentValues> employees = generateTestEmployees();

        for (ContentValues values : employees) {
            db.insert("employees", null, values);
        }
    }
}
