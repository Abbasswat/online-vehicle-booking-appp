package com.example.bookrides;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTitle, detailTime, detailPhone, detailAddress, detailDate, detailDestination,
            detailCompany_name, detailBus_types;
    ImageView detailImage;

    FloatingActionButton deleteButton,editButton;
    String key = "Key";
    private String imageUrl = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailTime = findViewById(R.id.detailTime);
        detailPhone = findViewById(R.id.detailphone);
        detailAddress = findViewById(R.id.detailAddress);
        detailDate = findViewById(R.id.detailDate);
        detailDestination = findViewById(R.id.detailDestination);
        detailCompany_name = findViewById(R.id.detailCompany_name);
        detailBus_types = findViewById(R.id.detailbus_types);
        detailImage = findViewById(R.id.detailImage);
        deleteButton = findViewById(R.id.deleteButton1);
        editButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailTime.setText(bundle.getString("Time"));
            detailPhone.setText(bundle.getString("Phone"));
            detailAddress.setText(bundle.getString("Address"));
            detailDate.setText(bundle.getString("Date"));
            detailDestination.setText(bundle.getString("Destination"));
            detailCompany_name.setText(bundle.getString("Company"));
            detailBus_types.setText(bundle.getString("Buses"));
           key= bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (key != null && !key.isEmpty()) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Long Travel vehicles")
                            .child(key);

                    databaseReference.removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(DetailActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                                    finish(); // Finish the activity after deletion
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("DetailActivity", "Error deleting data: " + e.getMessage());
                                    Toast.makeText(DetailActivity.this, "Error deleting data", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(DetailActivity.this, "Item key is missing", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}