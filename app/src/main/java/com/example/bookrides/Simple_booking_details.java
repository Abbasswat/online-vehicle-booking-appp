package com.example.bookrides;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Simple_booking_details extends AppCompatActivity {

    TextView detaildrivername,detailloaderemail,detailloaderphone,detailloaderlocation,detailloadername,detailloadervehiclenum,detailloaderdesc;
    ImageView detailloaderimage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_booking_details);

        detaildrivername = findViewById(R.id.LoaderDrivernameD_sp);
        detailloaderemail = findViewById(R.id.loaderDriverEmailD_sp);
        detailloaderphone = findViewById(R.id.LoaderDriverPhoneD_sp);
        detailloaderlocation= findViewById(R.id.loaderDriverLocationD_sp);
        detailloadername= findViewById(R.id.loaderVehiclenameD_sp);
        detailloadervehiclenum= findViewById(R.id.LoaderVehiclenumD_sp);
        detailloaderimage= findViewById(R.id.detailsimpleImageD);
//        detailloaderdesc= findViewById(R.id.detailLoaderDesc);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detaildrivername.setText(bundle.getString("name"));
            detailloaderemail.setText(bundle.getString("email"));
            detailloaderphone.setText(bundle.getString("phone"));
            detailloaderlocation.setText(bundle.getString("locations"));
            detailloadername.setText(bundle.getString("loaderType"));
            detailloadervehiclenum.setText(bundle.getString("loadernumber"));

            Glide.with(this).load(bundle.getString("Image")).into(detailloaderimage);
        }
    }
}