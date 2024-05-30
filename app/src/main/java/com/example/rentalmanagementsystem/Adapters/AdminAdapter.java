package com.example.rentalmanagementsystem.Adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalmanagementsystem.Entities.Admin;
import com.example.rentalmanagementsystem.R;
import com.example.rentalmanagementsystem.util.DatabaseHelper;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {

    private List<Admin> adminList;
    private Context context;
    private DatabaseHelper dbHelper;

    public AdminAdapter(List<Admin> adminList, Context context) {
        this.adminList = adminList;
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_item, parent, false);
        return new AdminViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        Admin admin = adminList.get(position);
        holder.tvUsername.setText(admin.getUsername());
        holder.tvLevel.setText(String.valueOf(admin.getLevel()));
        holder.btnDelete.setOnClickListener(v -> deleteAdmin(admin.getId(), position));
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername, tvLevel;
        public Button btnDelete;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    private void deleteAdmin(int id, int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("admins", "id=?", new String[]{String.valueOf(id)});
        if (rowsDeleted > 0) {
            adminList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Admin deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error deleting admin", Toast.LENGTH_SHORT).show();
        }
    }
}
