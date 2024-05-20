// CustomerAdapter.java
package com.example.rentalmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Context context;
    private List<Customer> customerList;
    private DatabaseHelper db;

    public CustomerAdapter(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_item, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Customer customer = customerList.get(position);
        holder.tvName.setText(customer.getName());
        holder.tvPhone.setText(customer.getPhone());
        holder.tvEmail.setText(customer.getEmail());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCustomer(customer, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvEmail;
        Button btnDelete;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    private void deleteCustomer(Customer customer, int position) {
        SQLiteDatabase database = db.getWritableDatabase();
        int rowsDeleted = database.delete("customers", "id=?", new String[]{String.valueOf(customer.getId())});
        if (rowsDeleted > 0) {
            customerList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Customer deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error deleting customer", Toast.LENGTH_SHORT).show();
        }
    }
}
