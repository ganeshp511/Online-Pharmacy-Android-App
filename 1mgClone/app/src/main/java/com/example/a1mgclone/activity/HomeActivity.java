package com.example.a1mgclone.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.a1mgclone.R;
import com.example.a1mgclone.adapter.MedicinesRecyclreViewAdapter;
import com.example.a1mgclone.model.Medicine;
import com.example.a1mgclone.network.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements MedicinesRecyclreViewAdapter.OnSelectListener{

    RecyclerView recyclerView;
    ArrayList<Medicine> medicines = new ArrayList<>();
    MedicinesRecyclreViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MedicinesRecyclreViewAdapter(this, medicines, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMedicines();
    }

    private void getMedicines() {
        API.request("/medicine/", Request.Method.GET)
                .call(new API.ResponseListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            medicines.clear();
                            for (int index = 0; index < data.length(); index++) {
                                JSONObject object = data.getJSONObject(index);
                                Medicine medicine = new Medicine();
                                medicine.setId(object.getInt("id"));
                                medicine.setCompany(object.getString("company"));
                                medicine.setMrp(object.getDouble("mrp"));
                                medicine.setPrice(object.getDouble("price"));
                                medicine.setUnit(object.getString("unit"));
                                medicine.setTitle(object.getString("title"));
                                medicine.setExpiryDate(object.getString("expiryDate"));
                                medicine.setThumbnail(object.getString("thumbnail"));

                                medicines.add(medicine);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }, new API.ErrorListener() {
                    @Override
                    public void onError(String error) {
                        Toast.makeText(HomeActivity.this, "error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onSelect(Medicine medicine) {
        Intent intent = new Intent(this, MedicineDetailsActivity.class);
        intent.putExtra("medicine", medicine);
        startActivity(intent);
    }

}