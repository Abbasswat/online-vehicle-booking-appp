package com.example.bookrides.RegisterVehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookrides.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookrides.RegisterDriver;
import com.example.bookrides.Register_Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Loader_vehicle extends AppCompatActivity {



    private CircleImageView profile_imageL;

    private EditText registerFullNameL,registerIdNumberL,
            registerPhoneNumberL, registerEmailL, registerPasswordL, registerLocationL;

    private Spinner LoaderVehicleSpinner;

    private Button registerButtonL;

    private Uri resultUriL;

    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;



//    @SuppressLint("MissingInflatedId")
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_vehicle);



        profile_imageL = findViewById(R.id.profile_imageL);
        registerFullNameL = findViewById(R.id.registerFullNameL);
        registerIdNumberL = findViewById(R.id.registerIdNumberL);
        registerPhoneNumberL = findViewById(R.id.registerPhoneNumberL);
        registerEmailL = findViewById(R.id.registerEmailL);
        registerPasswordL = findViewById(R.id.registerPasswordL);
        LoaderVehicleSpinner = findViewById(R.id.LoaderspinnerL);
        registerLocationL = findViewById(R.id.registerLocationL);
        registerButtonL = findViewById(R.id.registerButtonL);
        loader = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        profile_imageL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        registerButtonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailL = registerEmailL.getText().toString().trim();
                final  String passwordL = registerPasswordL.getText().toString().trim();
                final String fullNameL = registerFullNameL.getText().toString().trim();
                final String idNumberL = registerIdNumberL.getText().toString().trim();
                final String phoneNumberL = registerPhoneNumberL.getText().toString().trim();
                final String locationL = registerLocationL.getText().toString();
                final String LoaderVehicle = LoaderVehicleSpinner.getSelectedItem().toString();


                if (TextUtils.isEmpty(emailL)){
                    registerEmailL.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(passwordL)){
                    registerPasswordL.setError("Password is required!");
                    return;
                }
                if (TextUtils.isEmpty(fullNameL)){
                    registerFullNameL.setError("Full name is required is required!");
                    return;
                }
                if (TextUtils.isEmpty(idNumberL)){
                    registerIdNumberL.setError("Id Number is required!");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumberL)){
                    registerPhoneNumberL.setError("Phone Number is required!");
                    return;
                }
                if (TextUtils.isEmpty(locationL)){
                    registerLocationL.setError("Location is required!");
                    return;
                }
                if (LoaderVehicle.equals("Select your blood group")){
                    Toast.makeText(Loader_vehicle.this, "Select Blood group", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    loader.setMessage("Registering you...");
                    loader.setCanceledOnTouchOutside(false);
//                    loader.show();

                    mAuth.createUserWithEmailAndPassword(emailL, passwordL).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()){
                                String error = task.getException().toString();
                                Toast.makeText(Loader_vehicle.this, "Error" + error, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDatabaseRef = FirebaseDatabase.getInstance().getReference()
                                        .child("LoaderUser").child(currentUserId);
                                HashMap userInfo = new HashMap();
                                userInfo.put("idL", currentUserId);
                                userInfo.put("nameL", fullNameL);
                                userInfo.put("emailL", emailL);
                                userInfo.put("idnumberL", idNumberL);
                                userInfo.put("phonenumberL", phoneNumberL);
                                userInfo.put("locationL", locationL);
                                userInfo.put("Loadervehicle", LoaderVehicle);
                                userInfo.put("type", "Loader");
//                                userInfo.put("type1", "Loader");

                                userInfo.put("search", "Loader"+LoaderVehicle);

                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(Loader_vehicle.this, "Data set Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(Loader_vehicle.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        finish();
                                        //loader.dismiss();
                                    }
                                });

                                if (resultUriL !=null){
                                    final StorageReference filePath = FirebaseStorage.getInstance().getReference()
                                            .child("profile images").child(currentUserId);
                                    Bitmap bitmap = null;

                                    try {
                                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUriL);
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                    byte[] data  = byteArrayOutputStream.toByteArray();
                                    UploadTask uploadTask = filePath.putBytes(data);

                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Loader_vehicle.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            if (taskSnapshot.getMetadata() !=null && taskSnapshot.getMetadata().getReference() !=null){
                                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl = uri.toString();
                                                        Map newImageMap = new HashMap();
                                                        newImageMap.put("profilepictureurlL", imageUrl);

                                                        userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {
                                                                if (task.isSuccessful()){
                                                                    Toast.makeText(Loader_vehicle.this, "Image url added to database successfully", Toast.LENGTH_SHORT).show();
                                                                }else {
                                                                    Toast.makeText(Loader_vehicle.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                                        finish();
                                                    }
                                                });
                                            }

                                        }
                                    });

                                    Intent intent = new Intent(Loader_vehicle.this, Register_Vehicle.class);
                                    startActivity(intent);
                                    finish();
                                    loader.dismiss();
                                }
                            }

                        }
                    });
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == RESULT_OK && data !=null){
            resultUriL = data.getData();
            profile_imageL.setImageURI(resultUriL);
        }
    }
}