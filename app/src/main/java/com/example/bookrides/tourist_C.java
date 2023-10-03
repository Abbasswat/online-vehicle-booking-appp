package com.example.bookrides;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import com.example.bookrides.Adapter.TourAdapter;
import com.example.bookrides.Adapter.TourAdapter_C;
import com.example.bookrides.Model.TourModelClass;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class tourist_C extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    TourAdapter_C tourAdapter_c;
    androidx.appcompat.widget.SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_c);


        recyclerView = findViewById(R.id.recyclerViewTC);
        searchView =findViewById(R.id.searchTC);
        searchView.clearFocus();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<TourModelClass> options =
                new FirebaseRecyclerOptions.Builder<TourModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tour Booking"),TourModelClass.class)
                        .build();

        tourAdapter_c = new TourAdapter_C(options,tourist_C.this);
        recyclerView.setAdapter(tourAdapter_c);

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
        tourAdapter_c.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tourAdapter_c.stopListening();
    }
    public void txtSearch(String str){
        FirebaseRecyclerOptions<TourModelClass> options =
                new FirebaseRecyclerOptions.Builder<TourModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tour Booking").orderByChild("loaderlocation").startAt(str).endAt(str+"~"),TourModelClass.class)
                        .build();


        tourAdapter_c = new TourAdapter_C(options, tourist_C.this);
        tourAdapter_c.startListening();
        recyclerView.setAdapter(tourAdapter_c);
    }
    }
