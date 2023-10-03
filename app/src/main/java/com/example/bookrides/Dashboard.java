package com.example.bookrides;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.bookrides.RegisterVehicle.Loaders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity {

    CircleImageView userPhoto;
    TextView userName, userBloodGroup;
    FirebaseAuth auth;
    private DatabaseReference userRef;
    private CardView Tourist,SimpleBooking,Loader,SchoolBus,JobInfo,BusSeat,logout,gotoRegister;

//    Button btnDriverLogout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userPhoto = findViewById(R.id.dashboardUserPhotoId);
        userName = findViewById(R.id.dashboardUserNameId);
        userBloodGroup = findViewById(R.id.dashboardUserBloodGroupId);

        Tourist = findViewById(R.id.Tourist);
        SimpleBooking = findViewById(R.id.simpleBooking);
        Loader = findViewById(R.id.Loader);
        SchoolBus = findViewById(R.id.SchoolBus);
        JobInfo = findViewById(R.id.JobPost);
        BusSeat = findViewById(R.id.BusSeat);
logout = findViewById(R.id.Logout);
gotoRegister = findViewById(R.id.gotoregister);
auth = FirebaseAuth.getInstance();

    SimpleBooking.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Dashboard.this,MainActivity.class);
            startActivity(intent);
        }
    });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(Dashboard.this,DriverLogin.class);
                startActivity(intent);
            }
        });
        Tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,TouristMain.class);
                startActivity(intent);
            }
        });

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Register_Vehicle.class);
                startActivity(intent);
            }
        });
        Loader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Loaders.class);
                startActivity(intent);
            }
        });
        SchoolBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Practice.class);
                startActivity(intent);
            }
        });
        JobInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Job_Posts.class);
                startActivity(intent);
            }
        });
        BusSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Upload_Activity.class);
                startActivity(intent);
            }
        });
    }
}