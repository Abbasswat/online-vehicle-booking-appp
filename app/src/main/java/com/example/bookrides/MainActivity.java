package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.bookrides.Adapter.LoaderAdapter;
import com.example.bookrides.Adapter.TourAdapter;
import com.example.bookrides.Model.LoaderModelClass;
import com.example.bookrides.Model.TourModelClass;
import com.example.bookrides.RegisterVehicle.Loaders;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    androidx.appcompat.widget.SearchView searchView;
    TourAdapter tourAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewT);
        searchView = findViewById(R.id.searchS);
        searchView.clearFocus();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<TourModelClass> options =
                new FirebaseRecyclerOptions.Builder<TourModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tour Booking"),TourModelClass.class)
                        .build();

        tourAdapter = new TourAdapter(options,MainActivity.this);
        recyclerView.setAdapter(tourAdapter);

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
        tourAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tourAdapter.stopListening();
    }
    public void txtSearch(String str){
        FirebaseRecyclerOptions<TourModelClass> options =
                new FirebaseRecyclerOptions.Builder<TourModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tour Booking").orderByChild("loaderlocation").startAt(str).endAt(str+"~"),TourModelClass.class)
                        .build();


        tourAdapter = new TourAdapter(options, MainActivity.this);
        tourAdapter.startListening();
        recyclerView.setAdapter(tourAdapter);
    }
}