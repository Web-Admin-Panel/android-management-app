package com.example.rentalmanagementsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalmanagementsystem.util.DatabaseHelper;
import com.example.rentalmanagementsystem.R;
import com.example.rentalmanagementsystem.tests.*;

public class MainActivity extends AppCompatActivity {

    private Button btnCustomers, btnEmployees, btnApartments, btnGenerateTestData;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCustomers = findViewById(R.id.btnCustomers);
        btnEmployees = findViewById(R.id.btnEmployees);
        btnApartments = findViewById(R.id.btnApartments);

        btnGenerateTestData = findViewById(R.id.btnGenerateTestData);

        db = new DatabaseHelper(this);

        btnCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ManageCustomersActivity.class));
            }
        });

        btnEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ManageEmployeesActivity.class));
            }
        });

        btnApartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ManageApartmentsActivity.class));
            }
        });

        btnGenerateTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateTestData();
            }
        });
    }

    private void generateTestData() {
        TestCustomers.insertTestCustomers(db);
        TestEmployees.insertTestEmployees(db);
        TestApartments.insertTestApartments(db);
        Toast.makeText(this, "Random Customers were generated...", Toast.LENGTH_SHORT).show();
    }
}
