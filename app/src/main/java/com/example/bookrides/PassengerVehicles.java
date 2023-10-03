package com.example.bookrides;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.bookrides.Adapter.SimpleAdapter;
import com.example.bookrides.Model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class PassengerVehicles extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    SimpleAdapter simpleAdapter;
    androidx.appcompat.widget.SearchView searchView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);



        recyclerView = findViewById(R.id.practice_rv);
        searchView = findViewById(R.id.searchPr);
        searchView.clearFocus();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"),User.class)
                        .build();

        simpleAdapter = new SimpleAdapter(options,PassengerVehicles.this);
        recyclerView.setAdapter(simpleAdapter);

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
        simpleAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        simpleAdapter.stopListening();
    }



    public void txtSearch(String str){
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("location").startAt(str).endAt(str+"~"),User.class)
                        .build();


        simpleAdapter = new SimpleAdapter(options,PassengerVehicles.this);
        simpleAdapter.startListening();
        recyclerView.setAdapter(simpleAdapter);
    }
}