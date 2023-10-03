package com.example.bookrides;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.bookrides.Customer.Buses_customer;
import com.example.bookrides.Customer.Loader_C;
import com.example.bookrides.RegisterVehicle.Loaders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class CustomerDashboard extends AppCompatActivity {
    CircleImageView userPhoto;

//Button btnlogout;
    TextView userName, email;
    private DatabaseReference userRef;
    FirebaseAuth auth;
    private CardView Tourist,SimpleBooking,Loader,SchoolBus,JobInfo,BusSeat;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);
        userPhoto = findViewById(R.id.dashboardUserPhotoIdC);
        userName = findViewById(R.id.dashboardUserNameIdC);
//        email = findViewById(R.id.dashboardUserIdC);
//        btnlogout = findViewById(R.id.logoutbtn);
        Tourist = findViewById(R.id.Tourist);
        SimpleBooking = findViewById(R.id.simpleBooking);
        Loader = findViewById(R.id.Loader);
        SchoolBus = findViewById(R.id.SchoolBus);
        JobInfo = findViewById(R.id.JobPost);
        BusSeat = findViewById(R.id.BusSeat);
        auth= FirebaseAuth.getInstance();
        SimpleBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDashboard.this, tourist_C.class);
                startActivity(intent);
            }
        });
        Tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDashboard.this, TouristMain.class);
                startActivity(intent);
            }
        });
        Loader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDashboard.this, com.example.bookrides.Customer.Loader_C.class);
                startActivity(intent);
            }
        });
        SchoolBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDashboard.this, School_Vehicle.class);
                startActivity(intent);
            }
        });
        JobInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDashboard.this, Job_Posts.class);
                startActivity(intent);
            }
        });
        BusSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDashboard.this, Buses_customer.class);
                startActivity(intent);
            }
        });
    }
}
