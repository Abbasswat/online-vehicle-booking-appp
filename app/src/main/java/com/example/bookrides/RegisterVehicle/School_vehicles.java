package com.example.bookrides.RegisterVehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookrides.Model.LoaderModelClass;
import com.example.bookrides.Model.SchoolModelClass;
import com.example.bookrides.R;

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



import de.hdodenhof.circleimageview.CircleImageView;

public class School_vehicles extends AppCompatActivity {


    ImageView school_Image;
    MaterialButton save_school;
    EditText schoolname, schoolemail, schoolphone,schoollocation,schoolvehiclenumber,schooltime,collegename,schooldesc;
    Spinner schooltypespinner,studettpespinner;
    String imageURL;
    Uri uri;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_school_vehicles);




        school_Image = findViewById(R.id.schoolprofile_image);
        schoolname = findViewById(R.id.schoolregisterFullName);
        schoolemail = findViewById(R.id.schoolregisterEmail);
        schoolphone = findViewById(R.id.schoolregisterPhoneNumber);
        schoollocation = findViewById(R.id.schoolregisterLocation);
        schooltypespinner = findViewById(R.id.schoolvehicleTypeSpinner);
        studettpespinner = findViewById(R.id.students_type);
        schoolvehiclenumber = findViewById(R.id.schoolregisterIdNumber);
        schooltime = findViewById(R.id.schoolregisterTime);
        collegename = findViewById(R.id.schoolregistercolleges_name);
        schooldesc = findViewById(R.id.schoolregisterDesc);
        save_school = findViewById(R.id.schoolregisterButton);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            school_Image.setImageURI(uri);
                        } else {

                            Toast.makeText(School_vehicles.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        school_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        save_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }
    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(School_vehicles.this);
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
        String name = schoolname.getText().toString();
        String email = schoolemail.getText().toString();
        String phone = schoolphone.getText().toString();
        String location = schoollocation.getText().toString();

        String vehicltype = schooltypespinner.getSelectedItem().toString();
        String studenttype = studettpespinner.getSelectedItem().toString();
        String vehiclenum = schoolvehiclenumber.getText().toString();
        String time = schooltime.getText().toString();
        String collegenam =collegename.getText().toString();
        String schoolDescr =schooldesc.getText().toString();



        SchoolModelClass schoolModelClass = new SchoolModelClass(name,email,phone,location,vehicltype,studenttype,vehiclenum,time,collegenam,schoolDescr,imageURL);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.


        if (TextUtils.isEmpty(name)){
            schoolname.setError("please enter your name !");
            return;
        }if (TextUtils.isEmpty(email)){
            schoolemail.setError("please enter your email!");
            return;
        }if (TextUtils.isEmpty(phone)){
            schoolphone.setError("please enter your phone number");
            return;
        }if (TextUtils.isEmpty(location)){
            schoollocation.setError("please enter your location");
            return;
        }if (TextUtils.isEmpty(vehiclenum)){
            schoolvehiclenumber.setError("please enter vehicle number!");
            return;
        }if (TextUtils.isEmpty(time)){
            schooltime.setError("please enter school time!");
            return;
        }if (TextUtils.isEmpty(collegenam)){
            collegename.setError("please enter the college/school name!");
            return;
        }if (TextUtils.isEmpty(schoolDescr)){
            schooldesc.setError("please write some description");
            return;
        }else {
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("School Vehicles Booking").child(currentDate)
                .setValue(schoolModelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(School_vehicles.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(School_vehicles.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }}
}