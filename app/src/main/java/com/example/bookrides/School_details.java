package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class School_details extends AppCompatActivity {
    TextView detailnameS, detailemailS, detailtimeS,detailPhoneS,detailAddressS,detailsch_clg_nameS,detailvehiclenumberS
            ,detailvehicleTypeS,detailstudenttpeS, detaildescS;

    ImageView detailDriverImageS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);

        detailnameS = findViewById(R.id.detailnameS);
        detailemailS = findViewById(R.id.detailEmailS);
        detailAddressS= findViewById(R.id.detailDriverAddressS);
        detailPhoneS= findViewById(R.id.detailDriverphoneS);
        detailsch_clg_nameS= findViewById(R.id.School_college_name);
        detailstudenttpeS= findViewById(R.id.detailVehicletypeS);
        detailvehicleTypeS= findViewById(R.id.detailstudentTypes);
        detailvehiclenumberS= findViewById(R.id.detailVehiclenumS);
        detailtimeS= findViewById(R.id.detailTimeS);
        detaildescS= findViewById(R.id.school_desc);
        detailDriverImageS = findViewById(R.id.detailDriverImageS);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailnameS.setText(bundle.getString("nameS"));
            detailemailS.setText(bundle.getString("emailS"));
            detailAddressS.setText(bundle.getString("locationsS"));
            detailPhoneS.setText(bundle.getString("phoneS"));
            detailsch_clg_nameS.setText(bundle.getString("collegeNameS"));
            detailstudenttpeS.setText(bundle.getString("StudentsTypeS"));
            detailvehicleTypeS.setText(bundle.getString("schoolVehicleTypeS"));
            detailvehiclenumberS.setText(bundle.getString("SchoolVehiclenumS"));
            detailtimeS.setText(bundle.getString("timeS"));
            detaildescS.setText(bundle.getString("DescriptionS"));
            Glide.with(this).load(bundle.getString("Image")).into(detailDriverImageS);
        }
    }
}