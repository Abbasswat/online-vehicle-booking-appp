package com.example.bookrides.RegisterVehicle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import com.example.bookrides.Adapter.LoaderAdapter;
import com.example.bookrides.MainActivity;
import com.example.bookrides.MainAdapter;
import com.example.bookrides.MainModel;
import com.example.bookrides.Model.LoaderModelClass;
import com.example.bookrides.MyAdapter;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.widget.Button;
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



public class Loaders extends AppCompatActivity {
    FloatingActionButton fabL;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerViewL;
    List<LoaderModelClass> loaderModelClassList;
    LoaderAdapter loaderAdapter;
    androidx.appcompat.widget.SearchView searchView;
    Button check;
    private static final int SMS_PERMISSION_REQUEST_CODE = 101;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaders);



        recyclerViewL = findViewById(R.id.recyclerViewL);
//        check = findViewById(R.id.checkpermission);

//        fabL = findViewById(R.id.fabL);
        searchView = findViewById(R.id.searchL);
        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Loaders.this, 1);
        recyclerViewL.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(Loaders.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
//        dialog.show();
//        loaderModelClassList = new ArrayList<>();

//        check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Check and request SMS permissions
//                checkAndRequestPermissions();
//
//                // Rest of your delete button logic here
//            }
//        });

        FirebaseRecyclerOptions<LoaderModelClass> options =
                new FirebaseRecyclerOptions.Builder<LoaderModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Loader Booking"),LoaderModelClass.class)
                        .build();
        loaderAdapter = new LoaderAdapter(options,Loaders.this);
        recyclerViewL.setAdapter(loaderAdapter);


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
        loaderAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loaderAdapter.stopListening();
    }

    public void txtSearch(String str){
        FirebaseRecyclerOptions<LoaderModelClass> options =
                new FirebaseRecyclerOptions.Builder<LoaderModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Loader Booking").orderByChild("loaderlocation").startAt(str).endAt(str+"~"),LoaderModelClass.class)
                        .build();


        loaderAdapter = new LoaderAdapter(options, Loaders.this);
        loaderAdapter.startListening();
        recyclerViewL.setAdapter(loaderAdapter);
    }
//    private void checkAndRequestPermissions() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
//        } else {
//            // Permissions are already granted, you can send SMS
//            // Add your SMS sending logic here
//        }
//    }
}