package com.example.rentalmanagementsystem.Activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalmanagementsystem.Adapters.AdminAdapter;
import com.example.rentalmanagementsystem.Entities.Admin;
import com.example.rentalmanagementsystem.R;
import com.example.rentalmanagementsystem.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ManageAdminsActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtLevel;
    private Button btnAdd;
    private DatabaseHelper db;
    private RecyclerView recyclerViewAdmins;
    private AdminAdapter adminAdapter;
    private List<Admin> adminList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admins);

        db = new DatabaseHelper(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtLevel = findViewById(R.id.edtLevel);
        btnAdd = findViewById(R.id.btnAddAdmin);
//        btnView = findViewById(R.id.btnViewAdmins);
        recyclerViewAdmins = findViewById(R.id.recyclerViewAdmins);

        adminList = new ArrayList<>();
        adminAdapter = new AdminAdapter(adminList, this);
        recyclerViewAdmins.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdmins.setAdapter(adminAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdmin();
            }
        });

        loadAdmins();
    }

    private void addAdmin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String levelStr = edtLevel.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(levelStr)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int level = Integer.parseInt(levelStr);
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("level", level);

        long id = database.insert("admins", null, values);
        if (id != -1) {
            Toast.makeText(this, "Admin added", Toast.LENGTH_SHORT).show();
            edtUsername.setText("");
            edtPassword.setText("");
            edtLevel.setText("");
            loadAdmins();
        } else {
            Toast.makeText(this, "Error adding admin", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAdmins() {
        adminList.clear();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM admins", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                int level = cursor.getInt(cursor.getColumnIndexOrThrow("level"));

                Admin admin = new Admin(id, username, password, level);
                adminList.add(admin);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adminAdapter.notifyDataSetChanged();
    }
}
