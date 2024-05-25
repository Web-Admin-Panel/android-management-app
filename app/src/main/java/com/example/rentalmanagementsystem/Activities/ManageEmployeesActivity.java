package com.example.rentalmanagementsystem.Activities;

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

import com.example.rentalmanagementsystem.util.DatabaseHelper;
import com.example.rentalmanagementsystem.Entities.Employee;
import com.example.rentalmanagementsystem.Adapters.EmployeeAdapter;
import com.example.rentalmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class ManageEmployeesActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtEmail, edtPosition;
    private Button btnAdd, btnView;
    private DatabaseHelper db;
    private RecyclerView recyclerViewEmployees;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("BEDUB POINT");
        setContentView(R.layout.activity_manage_employees);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtPosition = findViewById(R.id.edtPosition);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        recyclerViewEmployees = findViewById(R.id.recyclerViewEmployees);

        db = new DatabaseHelper(this);
        employeeList = new ArrayList<>();

        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        employeeAdapter = new EmployeeAdapter(this, employeeList);
        recyclerViewEmployees.setAdapter(employeeAdapter);
        viewEmployees(); // Refresh the employee list

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewEmployees();
            }
        });
    }

    private void addEmployee() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String email = edtEmail.getText().toString();
        String position = edtPosition.getText().toString();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || position.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteDatabase database = db.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("phone", phone);
            values.put("email", email);
            values.put("position", position);

            long id = database.insert("employees", null, values);
            if (id > 0) {
                Toast.makeText(this, "Employee added", Toast.LENGTH_SHORT).show();
                edtName.setText("");
                edtPhone.setText("");
                edtEmail.setText("");
                edtPosition.setText("");
                viewEmployees(); // Refresh the employee list
            } else {
                Toast.makeText(this, "Error adding employee", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void viewEmployees() {
        employeeList.clear();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query("employees", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String position = cursor.getString(cursor.getColumnIndexOrThrow("position"));

                employeeList.add(new Employee(id, name, phone, email, position));
            }
            cursor.close();
        }

        employeeAdapter.notifyDataSetChanged();
    }
}
