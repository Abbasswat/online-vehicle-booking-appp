package com.example.bookrides.Customer;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookrides.Adapter.LoaderAdapter;
import com.example.bookrides.Adapter.LoaderAdapter_C;
import com.example.bookrides.MainActivity;
import com.example.bookrides.MainAdapter;
import com.example.bookrides.MainModel;
import com.example.bookrides.Model.LoaderModelClass;
import com.example.bookrides.MyAdapter;
import com.example.bookrides.Practice;
import com.example.bookrides.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.bookrides.Adapter.TourAdapter;
import com.example.bookrides.Model.DataClass;
import com.example.bookrides.Model.TourModelClass;
import com.example.bookrides.RegisterVehicle.Register_Cars;
import com.example.bookrides.RegisterVehicle.Tourist_vehicles;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;



public class Loader_C extends AppCompatActivity {
    FloatingActionButton fabL;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerViewL;
    List<LoaderModelClass> loaderModelClassList;
    LoaderAdapter_C loaderAdapter_c;
    androidx.appcompat.widget.SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_c);



        recyclerViewL = findViewById(R.id.recyclerViewL_C);
//        fabL = findViewById(R.id.fabL);
        searchView = findViewById(R.id.searchL_C);
//        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Loader_C.this, 1);
        recyclerViewL.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(Loader_C.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
//        dialog.show();
//        loaderModelClassList = new ArrayList<>();

        FirebaseRecyclerOptions<LoaderModelClass> options =
                new FirebaseRecyclerOptions.Builder<LoaderModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Loader Booking"),LoaderModelClass.class)
                        .build();
        loaderAdapter_c = new LoaderAdapter_C(options, Loader_C.this);
        recyclerViewL.setAdapter(loaderAdapter_c);


        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                txtSearch(newText);
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        loaderAdapter_c.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loaderAdapter_c.stopListening();
    }

    public void txtSearch(String str){
        FirebaseRecyclerOptions<LoaderModelClass> options =
                new FirebaseRecyclerOptions.Builder<LoaderModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Loader Booking").orderByChild("loaderlocation").startAt(str).endAt(str+"~"),LoaderModelClass.class)
                        .build();


        loaderAdapter_c = new LoaderAdapter_C(options, Loader_C.this);
        loaderAdapter_c.startListening();
        recyclerViewL.setAdapter(loaderAdapter_c);
    }


}