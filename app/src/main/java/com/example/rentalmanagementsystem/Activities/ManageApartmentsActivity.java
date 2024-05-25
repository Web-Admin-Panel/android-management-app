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

import com.example.rentalmanagementsystem.Entities.Apartment;
import com.example.rentalmanagementsystem.Adapters.ApartmentAdapter;
import com.example.rentalmanagementsystem.util.DatabaseHelper;
import com.example.rentalmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class ManageApartmentsActivity extends AppCompatActivity {

    private EditText edtAddress, edtRent;
    private Button btnAdd, btnView;
    private DatabaseHelper db;
    private RecyclerView recyclerViewApartments;
    private ApartmentAdapter apartmentAdapter;
    private List<Apartment> apartmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_apartments);

        edtAddress = findViewById(R.id.edtAddress);
        edtRent = findViewById(R.id.edtRent);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        recyclerViewApartments = findViewById(R.id.recyclerViewApartments);

        db = new DatabaseHelper(this);
        apartmentList = new ArrayList<>();

        recyclerViewApartments.setLayoutManager(new LinearLayoutManager(this));
        apartmentAdapter = new ApartmentAdapter(this, apartmentList);
        recyclerViewApartments.setAdapter(apartmentAdapter);
        viewApartments(); // Refresh the apartment list

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addApartment();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewApartments();
            }
        });
    }

    private void addApartment() {
        String address = edtAddress.getText().toString();
        String rentText = edtRent.getText().toString();

        if (address.isEmpty() || rentText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            double rent = Double.parseDouble(rentText);

            SQLiteDatabase database = db.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("address", address);
            values.put("rent", rent);

            long id = database.insert("apartments", null, values);
            if (id > 0) {
                Toast.makeText(this, "Apartment added", Toast.LENGTH_SHORT).show();
                edtAddress.setText("");
                edtRent.setText("");
                viewApartments(); // Refresh the apartment list
            } else {
                Toast.makeText(this, "Error adding apartment", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void viewApartments() {
        apartmentList.clear();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query("apartments", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                double rent = cursor.getDouble(cursor.getColumnIndexOrThrow("rent"));

                apartmentList.add(new Apartment(id, address, rent));
            }
            cursor.close();
        }

        apartmentAdapter.notifyDataSetChanged();
    }
}
