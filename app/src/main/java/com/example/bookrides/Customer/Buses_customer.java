package com.example.bookrides.Customer;

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

import com.example.bookrides.Adapter.MyAdapter_C;
import com.example.bookrides.MainModel;
import com.example.bookrides.Model.DataClass;
import com.example.bookrides.MyAdapter;
import com.example.bookrides.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Buses_customer extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    MyAdapter_C myAdapterC;
    androidx.appcompat.widget.SearchView searchView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_customer);



        recyclerView = findViewById(R.id.recyclerViewsC);
        searchView = findViewById(R.id.edittextSource_C);
        searchView.clearFocus();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<DataClass> options =
                new FirebaseRecyclerOptions.Builder<DataClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Long Travel vehicles"),DataClass.class)
                        .build();

        myAdapterC = new MyAdapter_C(options,Buses_customer.this);
        recyclerView.setAdapter(myAdapterC);

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
        myAdapterC.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapterC.stopListening();
    }



    public void txtSearch(String str){
        FirebaseRecyclerOptions<DataClass> options =
                new FirebaseRecyclerOptions.Builder<DataClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Long Travel vehicles").orderByChild("dataAddress").startAt(str).endAt(str+"~"),DataClass.class)
                        .build();


        myAdapterC = new MyAdapter_C(options,Buses_customer.this);
        myAdapterC.startListening();
        recyclerView.setAdapter(myAdapterC);
    }
}