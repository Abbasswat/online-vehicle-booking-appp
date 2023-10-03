package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class Welcome_Activity extends AppCompatActivity {
MaterialButton btnImDriver, btnImCustomer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnImDriver = findViewById(R.id.btnImDriver);
        btnImCustomer = findViewById(R.id.btnImCustomer);

        btnImDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome_Activity.this,Register_Vehicle.class);
                startActivity(intent);
            }
        });
        btnImCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome_Activity.this, CustomerDashboard.class);
                startActivity(intent);
            }
        });

    }
}