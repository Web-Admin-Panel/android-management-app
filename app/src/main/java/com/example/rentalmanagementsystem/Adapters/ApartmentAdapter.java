package com.example.rentalmanagementsystem.Adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.example.rentalmanagementsystem.util.DatabaseHelper;
import com.example.rentalmanagementsystem.Entities.Apartment;
import com.example.rentalmanagementsystem.R;
public class ApartmentAdapter extends RecyclerView.Adapter<ApartmentAdapter.ApartmentViewHolder> {

    private Context context;
    private List<Apartment> apartmentList;
    private DatabaseHelper dbHelper;

    public ApartmentAdapter(Context context, List<Apartment> apartmentList) {
        this.context = context;
        this.apartmentList = apartmentList;
        dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ApartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.apartment_item, parent, false);
        return new ApartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartmentViewHolder holder, int position) {
        Apartment apartment = apartmentList.get(position);
        holder.tvAddress.setText(apartment.getAddress());
        holder.tvRent.setText(String.valueOf(apartment.getRent()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteApartment(apartment.getId());
                apartmentList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, apartmentList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return apartmentList.size();
    }

    private void deleteApartment(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("apartments", "id=?", new String[]{String.valueOf(id)});
    }

    public static class ApartmentViewHolder extends RecyclerView.ViewHolder {

        TextView tvAddress, tvRent;
        Button btnDelete;

        public ApartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvRent = itemView.findViewById(R.id.tvRent);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
