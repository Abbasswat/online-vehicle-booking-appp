package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookrides.RegisterVehicle.Loader_vehicle;
import com.example.bookrides.RegisterVehicle.RegisterLoader;
import com.example.bookrides.RegisterVehicle.Register_Cars;
import com.example.bookrides.RegisterVehicle.Register_CarryDabba;
import com.example.bookrides.RegisterVehicle.Loaders;
import com.example.bookrides.RegisterVehicle.School_vehicles;
import com.example.bookrides.RegisterVehicle.Tourist_vehicles;
import com.google.android.material.button.MaterialButton;

public class Register_Vehicle extends AppCompatActivity {



    TextView gotoMAinActivity;
LinearLayout linearTours,linearloader,linearSchool,linearBuses,linearsimplebooking;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        linearTours = findViewById(R.id.lineartour);
        linearloader = findViewById(R.id.linearloader);
        linearSchool = findViewById(R.id.linearschool);
        linearBuses = findViewById(R.id.linearBuses);
        linearsimplebooking = findViewById(R.id.linearsimplebooking);

        gotoMAinActivity= findViewById(R.id.gotoMainActivity);

        linearTours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Vehicle.this, Tourist_vehicles.class);
                startActivity(intent);
            }
        });
        linearloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Vehicle.this, RegisterLoader.class);
                startActivity(intent);

            }
        });
        linearSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Vehicle.this, School_vehicles.class);
                startActivity(intent);
            }
        });
        linearBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Vehicle.this, Register_Cars.class);
                startActivity(intent);
            }
        });
        linearsimplebooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Vehicle.this, RegisterDriver.class);
                startActivity(intent);
            }
        });
        gotoMAinActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Vehicle.this, Dashboard.class);
                startActivity(intent);
            }
        });







    }
}