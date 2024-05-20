package com.example.rentalmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnCustomers, btnEmployees, btnApartments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCustomers = findViewById(R.id.btnCustomers);
        btnEmployees = findViewById(R.id.btnEmployees);
        btnApartments = findViewById(R.id.btnApartments);

        btnCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ManageCustomersActivity.class));
            }
        });

        btnEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ManageEmployeesActivity.class));
            }
        });

        btnApartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ManageApartmentsActivity.class));
            }
        });
    }
}
