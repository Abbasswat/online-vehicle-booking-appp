package com.example.bookrides;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Loader_details extends AppCompatActivity {

    TextView detaildrivername,detailloaderemail,detailloaderphone,detailloaderlocation,detailloadername,detailloadervehiclenum,detailloaderdesc;
    ImageView detailloaderimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_details);

        detaildrivername = findViewById(R.id.LoaderDrivernameD);
        detailloaderemail = findViewById(R.id.loaderDriverEmailD);
        detailloaderphone = findViewById(R.id.LoaderDriverPhoneD);
        detailloaderlocation= findViewById(R.id.loaderDriverLocationD);
        detailloadername= findViewById(R.id.loaderVehiclenameD);
        detailloadervehiclenum= findViewById(R.id.LoaderVehiclenumD);
        detailloaderimage= findViewById(R.id.detailLoaderImageD);
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