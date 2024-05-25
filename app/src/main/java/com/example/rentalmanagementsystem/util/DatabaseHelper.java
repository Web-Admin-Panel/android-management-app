package com.example.rentalmanagementsystem.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "apartment_rental.db";
    private static final int DATABASE_VERSION = 1;

    // Tables
    private static final String TABLE_CUSTOMERS = "customers";
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String TABLE_APARTMENTS = "apartments";

    // Columns for Customers
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";

    // Columns for Employees
    private static final String KEY_POSITION = "position";

    // Columns for Apartments
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_RENT = "rent";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMERS_TABLE = "CREATE TABLE " + TABLE_CUSTOMERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_CUSTOMERS_TABLE);

        String CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + TABLE_EMPLOYEES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_POSITION + " TEXT" + ")";
        db.execSQL(CREATE_EMPLOYEES_TABLE);

        String CREATE_APARTMENTS_TABLE = "CREATE TABLE " + TABLE_APARTMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_RENT + " REAL" + ")";
        db.execSQL(CREATE_APARTMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APARTMENTS);
        onCreate(db);
    }
}
