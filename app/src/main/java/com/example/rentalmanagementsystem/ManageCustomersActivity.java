package com.example.rentalmanagementsystem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageCustomersActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtEmail;
    private Button btnAdd, btnView;
    private DatabaseHelper db;
    private RecyclerView recyclerViewCustomers;
    private CustomerAdapter customerAdapter;
    private List<Customer> customerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customers);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        recyclerViewCustomers = findViewById(R.id.recyclerViewCustomers);

        db = new DatabaseHelper(this);
        customerList = new ArrayList<>();

        recyclerViewCustomers.setLayoutManager(new LinearLayoutManager(this));
        customerAdapter = new CustomerAdapter(this, customerList);
        recyclerViewCustomers.setAdapter(customerAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomer();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCustomers();
            }
        });
    }

    private void addCustomer() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String email = edtEmail.getText().toString();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteDatabase database = db.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("phone", phone);
            values.put("email", email);

            long id = database.insert("customers", null, values);
            if (id > 0) {
                Toast.makeText(this, "Customer added", Toast.LENGTH_SHORT).show();
                edtName.setText("");
                edtPhone.setText("");
                edtEmail.setText("");
            } else {
                Toast.makeText(this, "Error adding customer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void viewCustomers() {
        customerList.clear();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query("customers", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                customerList.add(new Customer(id, name, phone, email));
            }
            cursor.close();
        }

        customerAdapter.notifyDataSetChanged();
    }
}
