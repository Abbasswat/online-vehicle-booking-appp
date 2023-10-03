package com.example.bookrides.RegisterVehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.bookrides.Model.LoaderModelClass;
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



public class RegisterLoader extends AppCompatActivity {

    ImageView loaderimage;
    MaterialButton save_loader;
    EditText loadername, loaderemail, loaderphone,loaderlocation,vehiclenumber;
    EditText loadertypespinner;
    String imageURL;
    Uri uri;

    private ProgressDialog loader;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_loader);


        loaderimage = findViewById(R.id.Loader_profile_image);
        loadername = findViewById(R.id.registerLoaderFullName);
        loaderemail = findViewById(R.id.registerLoaderEmail);
        loaderphone = findViewById(R.id.registerLoaderPhoneNumber);
        loaderlocation = findViewById(R.id.registerLoaderLocation1);
        loadertypespinner = findViewById(R.id.registervehiclename);
        vehiclenumber = findViewById(R.id.registervehicleNumber);
        save_loader = findViewById(R.id.registerLoaderButton);
        loader = new ProgressDialog(this);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            loaderimage.setImageURI(uri);
                        } else {
                            Toast.makeText(RegisterLoader.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        loaderimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        save_loader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }
    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterLoader.this);
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

        String name = loadername.getText().toString();
        String email = loaderemail.getText().toString();
        String phone = loaderphone.getText().toString();
        String location = loaderlocation.getText().toString();

        String vehicltype = loadertypespinner.getText().toString();
        String vehiclenum = vehiclenumber.getText().toString();


        LoaderModelClass loaderModelClass = new LoaderModelClass(name,email,phone,location,vehicltype,vehiclenum,imageURL);
//        We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.

        if (TextUtils.isEmpty(name)){
            loadername.setError("please enter your name!");
            return;
        }if (TextUtils.isEmpty(email)){
            loaderemail.setError("please enter your email");
            return;
        }if (TextUtils.isEmpty(phone)){
            loaderphone.setError("please enter your phon number");
            return;
        }if (TextUtils.isEmpty(location)){
            loaderlocation.setError("please enter your location");
            return;
        }if (TextUtils.isEmpty(vehicltype)){
            loadertypespinner.setError("please enter your vehicle name!");
            return;
        }if (TextUtils.isEmpty(vehiclenum)){
            vehiclenumber.setError("enter your vehicle number!");
            return;
        }else {
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Loader Booking").child(currentDate)
                .setValue(loaderModelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterLoader.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterLoader.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }}
}