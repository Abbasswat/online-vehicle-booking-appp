package com.example.bookrides.RegisterVehicle;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookrides.Model.DataClass;
import com.example.bookrides.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.DateFormat;
import java.util.Calendar;
public class Register_Cars extends AppCompatActivity {

    ImageView uploadImage;
    private DatePickerDialog datePickerDialog;
    private EditText datepickerButton;
    Button saveButton;
    EditText uploadTopic, uploadDesc, uploadLang ,uploadphone,uploadAddress,uploaddestinationAddress,companyName;

    Spinner Bus_types;
    String imageURL;
    Uri uri;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);

        initDataPicker();

        datepickerButton = findViewById(R.id.uploadDatepicker);

        datepickerButton.setText(getTodayDate());
        uploadImage = findViewById(R.id.uploadImage);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadTopic = findViewById(R.id.uploadTopic);
        uploadLang = findViewById(R.id.UploadTime);
        uploadphone = findViewById(R.id.uploadPhone);
        uploadAddress = findViewById(R.id.uploadAddress);
        uploaddestinationAddress= findViewById(R.id.uploaddestinationAddress);
        companyName = findViewById(R.id.CompanyName);
        Bus_types = findViewById(R.id.Bus_vehicle_types);
        saveButton = findViewById(R.id.saveButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(Register_Cars.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
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
                datepickerButton.setText(date);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Register_Cars.this);
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
        String title = uploadTopic.getText().toString();
        String desc = uploadDesc.getText().toString();
        String lang = uploadLang.getText().toString();
        String phone = uploadphone.getText().toString();
        String address = uploadAddress.getText().toString();
        String date = datepickerButton.getText().toString();
        String destination = uploaddestinationAddress.getText().toString();
        String company = companyName.getText().toString();
        String buses =Bus_types.getSelectedItem().toString();

//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String customKey = FirebaseDatabase.getInstance().getReference("Long Travel vehicles").push().getKey();

        DataClass dataClass = new DataClass(title, desc, lang,phone,address,date,destination,company,buses,imageURL);
        //We are changing the child from title to currentDate,
//        DataClass dataClass = new DataClass(title, desc, lang, phone, address, date, destination, company, buses, imageURL);
        dataClass.setKey(title);
//        dataClass.setUserId(userId);
        // because we will be updating title as well and it may affect child value.


        if (TextUtils.isEmpty(title)){
            uploadTopic.setError("this field is required!");
            return;
        }if (TextUtils.isEmpty(desc)){
            uploadDesc.setError("this field is required!");
            return;
        }if (TextUtils.isEmpty(lang)){
            uploadLang.setError("please enter price is");
            return;
        }if (TextUtils.isEmpty(phone)){
            uploadphone.setError("please enter mobile number");
            return;
        }if (TextUtils.isEmpty(address)){
            uploadAddress.setError("this field is required!");
            return;
        }if (TextUtils.isEmpty(date)){
            datepickerButton.setError("please enter the company name!");
            return;
        }if (TextUtils.isEmpty(destination)){
            uploaddestinationAddress.setError("please write some description");
            return;
        }if (TextUtils.isEmpty(company)){
            companyName.setError("please enter date!");
            return;
        }else {

        FirebaseDatabase.getInstance().getReference("Long Travel vehicles").child(customKey)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register_Cars.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register_Cars.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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