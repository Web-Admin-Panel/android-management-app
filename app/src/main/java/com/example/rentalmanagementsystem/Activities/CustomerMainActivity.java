package com.example.rentalmanagementsystem.Activities;
import com.example.rentalmanagementsystem.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.rentalmanagementsystem.util.IntervalType;
import com.example.rentalmanagementsystem.Entities.BillType;
import com.example.rentalmanagementsystem.Entities.BillingModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerMainActivity extends AppCompatActivity {
    private Context context;
    private BillingModel mainViewModel;

    List<String> billTypes = new ArrayList<>();
    List<String> intervalTypes = new ArrayList<>();

    MaterialButton calculateButton;
    MaterialButton payNowButton;

    AppCompatEditText stayDurationEditText;

    Spinner billTypeSpinner;

    Spinner intervalTypeSpinner;

    TextView costPerMonthTextView;
    TextView totalCostTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        context = this;

        mainViewModel = new ViewModelProvider(this).get(BillingModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bill Calculator");
        setSupportActionBar(toolbar);

        for (BillType billType : BillType.values()) {
            billTypes.add(billType.displayTitle);
        }

        for (IntervalType intervalType : IntervalType.values()) {
            intervalTypes.add(intervalType.displayTitle) ;
        }

        costPerMonthTextView = findViewById(R.id.cost_per_month_text_view);
        totalCostTextView = findViewById(R.id.total_cost_text_view);

        stayDurationEditText = findViewById(R.id.stay_duration_edit_text);

        stayDurationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateButton.setEnabled(!s.toString().isEmpty() && !s.toString().equals("0"));
            }

            @Override
            public void afterTextChanged(Editable s) {
                costPerMonthTextView.setVisibility(View.GONE);
                totalCostTextView.setVisibility(View.GONE);
                mainViewModel.onStayDurationChanged(s.toString());
            }
        });

        billTypeSpinner = findViewById(R.id.bill_type_spinner);
        ArrayAdapter<String> billTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, billTypes);
        billTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billTypeSpinner.setAdapter(billTypeAdapter);
        billTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                costPerMonthTextView.setVisibility(View.GONE);
                totalCostTextView.setVisibility(View.GONE);
                payNowButton.setEnabled(false);
                List<BillType> billTypeList = Arrays.asList(BillType.values());
                mainViewModel.onBillTypeSelected(billTypeList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        intervalTypeSpinner = findViewById(R.id.interval_type_spinner);
        ArrayAdapter<String> intervalTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, intervalTypes);
        intervalTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalTypeSpinner.setAdapter(intervalTypeAdapter);

        intervalTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                costPerMonthTextView.setVisibility(View.GONE);
                totalCostTextView.setVisibility(View.GONE);
                payNowButton.setEnabled(false);
                List<IntervalType> intervalTypeList = Arrays.asList(IntervalType.values());
                mainViewModel.onIntervalTypeSelected(intervalTypeList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculateButton = findViewById(R.id.calculate_button);

        calculateButton.setOnClickListener(v -> {
            String stayDuration = stayDurationEditText.getText().toString();
            if (stayDuration.isEmpty() || stayDuration.equals("0")) {
                Toast.makeText(context, "Please enter a duration First", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    Integer.parseInt(stayDuration);
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Please enter a Valid duration First", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                mainViewModel.calculateBill();
                updateResults();
                payNowButton.setEnabled(true);
            }
        });

        payNowButton = findViewById(R.id.pay_now_button);
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerMainActivity.this, PaymentActivity.class));
                finish();
            }
        });

    }

    private void updateResults() {
        costPerMonthTextView.setVisibility(View.VISIBLE);
        totalCostTextView.setVisibility(View.VISIBLE);
        if (mainViewModel.getCostPerMonth() != 0 && mainViewModel.getTotalCost() != 0) {
            costPerMonthTextView.setText("Cost Per Month - $" + mainViewModel.getCostPerMonth() + " TL");
            totalCostTextView.setText("Total " + mainViewModel.getSelectedBillType() + " - $" + mainViewModel.getTotalCost() + " TL");
        }

    }
}