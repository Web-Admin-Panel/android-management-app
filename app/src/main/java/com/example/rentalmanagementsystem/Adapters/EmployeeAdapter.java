package com.example.rentalmanagementsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalmanagementsystem.util.DatabaseHelper;
import com.example.rentalmanagementsystem.Entities.Employee;
import com.example.rentalmanagementsystem.R;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Context context;
    private List<Employee> employeeList;
    private DatabaseHelper dbHelper;

    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
        dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employee_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Employee employee = employeeList.get(position);
        holder.tvName.setText(employee.getName());
        holder.tvPhone.setText(employee.getPhone());
        holder.tvEmail.setText(employee.getEmail());
        holder.tvPosition.setText(employee.getPosition());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employee.getId());
                employeeList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, employeeList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    private void deleteEmployee(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("employees", "id=?", new String[]{String.valueOf(id)});
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone, tvEmail, tvPosition;
        Button btnDelete;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
