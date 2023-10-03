
package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookrides.Adapter.JobAdapter;
import com.example.bookrides.Adapter.TourAdapter;
import com.example.bookrides.Model.DataClass;
import com.example.bookrides.Model.JobModelClass;
import com.example.bookrides.Model.TourModelClass;
import com.example.bookrides.RegisterVehicle.Upload_JobPosts;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class Job_Posts extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fabJ;

    Context context;
    JobAdapter jobAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posts);

        recyclerView = findViewById(R.id.recyclerViewJ);
        fabJ = findViewById(R.id.fabJ);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<JobModelClass> options =
                new FirebaseRecyclerOptions.Builder<JobModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Job posts"),JobModelClass.class)
                        .build();

        jobAdapter = new JobAdapter(options,Job_Posts.this);
        recyclerView.setAdapter(jobAdapter);
        fabJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Job_Posts.this, Upload_JobPosts.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        jobAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        jobAdapter.stopListening();
    }
}
