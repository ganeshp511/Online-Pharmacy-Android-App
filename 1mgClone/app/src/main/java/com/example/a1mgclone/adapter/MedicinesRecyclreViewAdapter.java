package com.example.a1mgclone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a1mgclone.R;
import com.example.a1mgclone.model.Medicine;

import java.util.ArrayList;

public class MedicinesRecyclreViewAdapter extends RecyclerView.Adapter<MedicinesRecyclreViewAdapter.ViewHolder> {



    public interface OnSelectListener {
        void onSelect(Medicine medicine);
    }

    Context context;
    ArrayList<Medicine> medicines;
    OnSelectListener listener;

    public MedicinesRecyclreViewAdapter(Context context, ArrayList<Medicine> medicines, OnSelectListener listener) {
        this.context = context;
        this.medicines = medicines;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_item_medicine, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Medicine medicine = medicines.get(position);

        holder.textTitle.setText(medicine.getTitle());
        holder.textCompany.setText(medicine.getCompany());
        holder.textMRP.setText("₹ " + medicine.getMrp());
        holder.textPrice.setText("₹ " + medicine.getPrice());

        String imageUrl = "http://192.168.1.4/medicine/image/" + medicine.getThumbnail();
        Log.e("Adapter", "image url = " + imageUrl);
        Glide.with(context).load(imageUrl).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelect(medicine);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textPrice, textMRP, textCompany;
        ImageView imageView;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.imageView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textPrice = itemView.findViewById(R.id.textPrice);
            textMRP = itemView.findViewById(R.id.textMRP);
            textCompany = itemView.findViewById(R.id.textCompany);
        }
    }
}
