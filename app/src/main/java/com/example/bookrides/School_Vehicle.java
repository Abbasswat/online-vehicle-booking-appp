package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.view.View;

import com.example.bookrides.Adapter.SchoolAdapter;
import com.example.bookrides.Model.DataClass;

import com.example.bookrides.Model.SchoolModelClass;
import com.example.bookrides.Model.TourModelClass;
import com.example.bookrides.RegisterVehicle.School_vehicles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class School_Vehicle extends AppCompatActivity {
    FloatingActionButton fab_S;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView_S;
    List<SchoolModelClass> schoolModelClassList;
    SchoolAdapter schoolAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_vehicle);

        recyclerView_S = findViewById(R.id.recyclerView_sch);
//        fab_S = findViewById(R.id.fab_sch);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(School_Vehicle.this, 1);
        recyclerView_S.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(School_Vehicle.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        schoolModelClassList = new ArrayList<>();
        schoolAdapter = new SchoolAdapter(School_Vehicle.this, schoolModelClassList);
        recyclerView_S.setAdapter(schoolAdapter);


        databaseReference =FirebaseDatabase.getInstance().getReference("School Vehicles Booking");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                schoolModelClassList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    SchoolModelClass schoolModelClass = itemSnapshot.getValue(SchoolModelClass.class);
//                    schoolModelClass.setKey(itemSnapshot.getKey());
                    schoolModelClassList.add(schoolModelClass);
                }
                schoolAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchList(newText);
                return true;
            }
        });

//        fab_S.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(School_Vehicle.this , School_vehicles.class);
//                startActivity(intent);
//            }
//        });


        databaseReference = FirebaseDatabase.getInstance().getReference("School Vehicles Booking");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                schoolModelClassList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    SchoolModelClass schoolModelClass = itemSnapshot.getValue(SchoolModelClass.class);
                    schoolModelClassList.add(schoolModelClass);

                }
                schoolAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }

        });
    }

    public void searchList(String text) {
        ArrayList<SchoolModelClass> searchList = new ArrayList<>();
        for (SchoolModelClass schoolModelClass : schoolModelClassList) {
            String destination = schoolModelClass.getSch_location();
            if (destination != null && destination.toLowerCase().contains(text.toLowerCase())) {
                searchList.add(schoolModelClass);
            }
        }
        schoolAdapter.searchDataList(searchList);
    }

}