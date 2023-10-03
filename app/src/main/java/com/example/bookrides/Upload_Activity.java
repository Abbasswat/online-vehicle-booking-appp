
package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.bookrides.Adapter.LoaderAdapter;
import com.example.bookrides.Adapter.TourAdapter;
import com.example.bookrides.Model.DataClass;
import com.example.bookrides.Model.LoaderModelClass;
import com.example.bookrides.Model.TourModelClass;
import com.example.bookrides.RegisterVehicle.Loaders;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Upload_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
        androidx.appcompat.widget.SearchView  editTextdestination,edittextsource,editTextD;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_activity);

        recyclerView = findViewById(R.id.recyclerViews);
                edittextsource=findViewById(R.id.edittextSource);
        editTextdestination= findViewById(R.id.editTextDestination);
                edittextsource.clearFocus();
                editTextdestination.clearFocus();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<DataClass> options =
                new FirebaseRecyclerOptions.Builder<DataClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Long Travel vehicles"),DataClass.class)
                        .build();
        myAdapter = new MyAdapter(options,Upload_Activity.this);
        recyclerView.setAdapter(myAdapter);

               edittextsource.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

editTextdestination.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
txtSearchDes(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        txtSearchDes(newText);
        return true;
    }
});

    }
    public void txtSearch(String str){
        FirebaseRecyclerOptions<DataClass> options =
                new FirebaseRecyclerOptions.Builder<DataClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Long Travel vehicles").orderByChild("dataAddress").startAt(str).endAt(str+"~"),DataClass.class)
                        .build();


        myAdapter = new MyAdapter(options, Upload_Activity.this);
        myAdapter.startListening();
        recyclerView.setAdapter(myAdapter);
    }
    public void txtSearchDes(String str){
        FirebaseRecyclerOptions<DataClass> options =
                new FirebaseRecyclerOptions.Builder<DataClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Long Travel vehicles").orderByChild("dataDestination").startAt(str).endAt(str+"~"),DataClass.class)
                        .build();


        myAdapter = new MyAdapter(options, Upload_Activity.this);
        myAdapter.startListening();
        recyclerView.setAdapter(myAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }
}





