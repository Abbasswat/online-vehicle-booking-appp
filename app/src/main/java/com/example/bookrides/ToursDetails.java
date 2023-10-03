package com.example.bookrides;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ToursDetails extends AppCompatActivity {

    TextView detailtourplace, detailtripDate, detailtripTime,detailtripPhone,detailtripAddress,detailtripVehicle,
            detailtripVehicleNum, detailtripDuration,detailtripPrice,detailtripPlanner,detailtripDesc;
    ImageView detailtripImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours_details);


        detailtourplace = findViewById(R.id.detailToursTitle);
        detailtripDate = findViewById(R.id.detailDateTr);
        detailtripTime = findViewById(R.id.detailTimeTr);
        detailtripPhone= findViewById(R.id.detailphoneTr);
        detailtripAddress= findViewById(R.id.detailAddressTr);
        detailtripVehicle= findViewById(R.id.detailVehicletypeTr);
        detailtripVehicleNum= findViewById(R.id.detailVehiclenumTr);
        detailtripDuration= findViewById(R.id.detailTripDuration);
        detailtripPrice= findViewById(R.id.detailTripPrice);
        detailtripDesc= findViewById(R.id.detailtripDescTr);
        detailtripPlanner= findViewById(R.id.detailTripplanner);
        detailtripImage= findViewById(R.id.detailTripImage);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailtourplace.setText(bundle.getString("title"));
            detailtripDate.setText(bundle.getString("TripDate"));
            detailtripTime.setText(bundle.getString("TripTime"));
            detailtripPhone.setText(bundle.getString("phone"));
            detailtripAddress.setText(bundle.getString("startingPlace"));
            detailtripVehicle.setText(bundle.getString("TripVehicle"));
            detailtripVehicleNum.setText(bundle.getString("TripVehicleNum"));
            detailtripDuration.setText(bundle.getString("Duration"));
            detailtripPrice.setText(bundle.getString("Price"));
            detailtripDesc.setText(bundle.getString("Description"));
            detailtripPlanner.setText(bundle.getString("Planner"));

            Glide.with(this).load(bundle.getString("TripImage")).into(detailtripImage);
        }
    }
}