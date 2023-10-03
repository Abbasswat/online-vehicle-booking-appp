package com.example.bookrides.RegisterVehicle;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookrides.Model.TourModelClass;
import com.example.bookrides.R;



import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookrides.Model.DataClass;
import com.example.bookrides.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.DateFormat;
import java.util.Calendar;


public class Tourist_vehicles extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private EditText tourDate;
    ImageView tourimage;
    MaterialButton save_Button;
    EditText tour_title, tour_price, tour_phone,tour_duration,tour_startlocation,tour_planner,
            tourTime, tour_desc,Tour_vehicleNum;
    Spinner tour_vehicles;
    String imageURL;
    Uri uri;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_vehicles);


        initDataPicker();
        tourDate = findViewById(R.id.tourDatepicker);

        tourDate.setText(getTodayDate());


        tourimage = findViewById(R.id.tour_image);
        tour_title = findViewById(R.id.tour_title);
        tour_price = findViewById(R.id.tourprice);
        tour_duration = findViewById(R.id.tourduration);
        tour_phone = findViewById(R.id.tourphone);
        tour_planner = findViewById(R.id.tourplanner);
        tour_startlocation = findViewById(R.id.tourlocation);
        tour_desc = findViewById(R.id.tour_desc);
        tourDate = findViewById(R.id.tourDatepicker);
        tourTime = findViewById(R.id.tourTime);
        tour_vehicles = findViewById(R.id.tour_vehicle_types);
        Tour_vehicleNum = findViewById(R.id.tour_vehiclesNum);
        save_Button = findViewById(R.id.tour_register);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            tourimage.setImageURI(uri);
                        } else {
                            Toast.makeText(Tourist_vehicles.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        tourimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }


    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);

    }
    private void initDataPicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month + 1;
                String date = makeDateString(day , month, year);
                tourDate.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this,style, dateSetListener, year , month, day);

    }


    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(Tourist_vehicles.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void uploadData(){
        String title = tour_title.getText().toString();

        String price = tour_price.getText().toString();
        String phone = tour_phone.getText().toString();
        String location = tour_startlocation.getText().toString();
        String duration = tour_duration.getText().toString();
        String planner = tour_planner.getText().toString();
        String desc = tour_desc.getText().toString();
        String DateT = tourDate.getText().toString();
        String TimeT = tourTime.getText().toString();
        String vehiclesTypeT = tourTime.getText().toString();
        String vehiclenumT = Tour_vehicleNum.getText().toString();

        TourModelClass tourModelClass = new TourModelClass(title,price,phone,location,duration,planner,desc,DateT,TimeT,vehiclesTypeT,vehiclenumT,imageURL);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        if (TextUtils.isEmpty(title)){
            tour_title.setError("this field is required!");
            return;
        }if (TextUtils.isEmpty(title)){
            tour_title.setError("this field is required!");
            return;
        }if (TextUtils.isEmpty(price)){
            tour_price.setError("please enter price is");
            return;
        }if (TextUtils.isEmpty(phone)){
            tour_phone.setError("please enter mobile number");
            return;
        }if (TextUtils.isEmpty(location)){
            tour_startlocation.setError("please enter location!");
            return;
        }if (TextUtils.isEmpty(duration)){
            tour_duration.setError("this field is required!");
            return;
        }if (TextUtils.isEmpty(planner)){
            tour_planner.setError("please enter the company name!");
            return;
        }if (TextUtils.isEmpty(desc)){
            tour_desc.setError("please write some description");
            return;
        }if (TextUtils.isEmpty(DateT)){
            tourDate.setError("please enter date!");
            return;
        }if (TextUtils.isEmpty(TimeT)){
            tourTime.setError("please enter time!");
            return;
        }if (TextUtils.isEmpty(vehiclenumT)){
            Tour_vehicleNum.setError("please enter vehicle number!");
            return;
        }else {
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Tour Booking").child(currentDate)
                .setValue(tourModelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Tourist_vehicles.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Tourist_vehicles.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }}
    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year ;
    }
    private String getMonthFormat(int month) {
        if (month == 1)
            return "Jan";
        if (month == 2)
            return "Feb";
        if (month == 3)
            return "March";
        if (month == 4)
            return "Aprail";
        if (month == 5)
            return "May";
        if (month == 6)
            return "June";
        if (month == 7)
            return "July";
        if (month == 8)
            return "August";
        if (month == 9)
            return "Sep";
        if (month == 10)
            return "Oct";
        if (month == 11)
            return "Nov";
        if (month == 12)
            return "Dec";

        return "Jan";

    }
    public void openDatepicker(View view) {
        datePickerDialog.show();
    }
}