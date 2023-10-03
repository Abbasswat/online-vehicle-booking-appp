package com.example.bookrides.RegisterVehicle;


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

import com.example.bookrides.MainActivity;
import com.example.bookrides.R;
import com.example.bookrides.RegisterDriver;
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

public class Register_CarryDabba extends AppCompatActivity {



    private CircleImageView profile_image;
FirebaseDatabase firebaseDatabase;
    private EditText registerFullName,registerIdNumber,
            registerPhoneNumber, registerEmail, registerPassword, registerLocation;

    private Spinner bloodGroupsSpinner;

    private Button registerButton;

    private Uri resultUri;

    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_carry_dabba);

//
//firebaseDatabase = FirebaseDatabase.getInstance();
//        profile_image = findViewById(R.id.profile_image);
//        registerFullName = findViewById(R.id.registerFullName);
//        registerIdNumber = findViewById(R.id.registerIdNumber);
//        registerPhoneNumber = findViewById(R.id.registerPhoneNumber);
//        registerEmail = findViewById(R.id.registerEmail);
//        registerPassword = findViewById(R.id.registerPassword);
//        bloodGroupsSpinner = findViewById(R.id.vehicleTypeSpinner);
//        registerLocation = findViewById(R.id.registerLocation);
//        registerButton = findViewById(R.id.registerButton);
//        loader = new ProgressDialog(this);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        profile_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);
//            }
//        });
//
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                final String email = registerEmail.getText().toString().trim();
////                final  String password = registerPassword.getText().toString().trim();
//                final String fullName = registerFullName.getText().toString().trim();
//                final String idNumber = registerIdNumber.getText().toString().trim();
//                final String phoneNumber = registerPhoneNumber.getText().toString().trim();
//                final String location = registerLocation.getText().toString();
//                final String bloodGroup = bloodGroupsSpinner.getSelectedItem().toString();
//
//
//
//                if (TextUtils.isEmpty(fullName)){
//                    registerFullName.setError("Full name is required is required!");
//                    return;
//                }
//                if (TextUtils.isEmpty(idNumber)){
//                    registerIdNumber.setError("Id Number is required!");
//                    return;
//                }
//                if (TextUtils.isEmpty(phoneNumber)){
//                    registerPhoneNumber.setError("Phone Number is required!");
//                    return;
//                }
//                if (TextUtils.isEmpty(location)){
//                    registerLocation.setError("Location is required!");
//                    return;
//                }
//                if (bloodGroup.equals("Select your blood group")){
//                    Toast.makeText(Register_CarryDabba.this, "Select Blood group", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                else {
//                    loader.setMessage("Registering you...");
//                    loader.setCanceledOnTouchOutside(false);
////                    loader.show();
//
//
//                                String currentUserId = mAuth.getCurrentUser().getUid();
//                                userDatabaseRef = FirebaseDatabase.getInstance().getReference()
//                                        .child("Carry Dabba").child(currentUserId);
//                                HashMap userInfo = new HashMap();
//                                userInfo.put("id", currentUserId);
//                                userInfo.put("name", fullName);
////                                userInfo.put("email", email);
//                                userInfo.put("idnumber", idNumber);
//                                userInfo.put("phonenumber", phoneNumber);
//                                userInfo.put("location", location);
//                                userInfo.put("bloodgroup", bloodGroup);
//                                userInfo.put("type", bloodGroup);
//                                userInfo.put("search", "driver"+bloodGroup);
//
//                                firebaseDatabase.getReference().child("CarryDabba").push().setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(Register_CarryDabba.this, "Register Successful", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                             {
//
//
//                                        finish();
//                                        //loader.dismiss();
//                                    }
//                                });
//
//                                if (resultUri !=null){
//                                    final StorageReference filePath = FirebaseStorage.getInstance().getReference()
//                                            .child("profile images").child(currentUserId);
//                                    Bitmap bitmap = null;
//
//                                    try {
//                                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
//                                    }catch (IOException e){
//                                        e.printStackTrace();
//                                    }
//                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
//                                    byte[] data  = byteArrayOutputStream.toByteArray();
//                                    UploadTask uploadTask = filePath.putBytes(data);
//
//                                    uploadTask.addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(Register_CarryDabba.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                            if (taskSnapshot.getMetadata() !=null && taskSnapshot.getMetadata().getReference() !=null){
//                                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
//                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                    @Override
//                                                    public void onSuccess(Uri uri) {
//                                                        String imageUrl = uri.toString();
//                                                        Map newImageMap = new HashMap();
//                                                        newImageMap.put("profilepictureurl", imageUrl);
//
//                                                        userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task task) {
//                                                                if (task.isSuccessful()){
//                                                                    Toast.makeText(Register_CarryDabba.this, "Image url added to database successfully", Toast.LENGTH_SHORT).show();
//                                                                }else {
//                                                                    Toast.makeText(Register_CarryDabba.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
//
//                                                        finish();
//                                                    }
//                                                });
//                                            }
//
//                                        }
//                                    });
//
//                                    Intent intent = new Intent(Register_CarryDabba.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                    loader.dismiss();
//                                }
//                            }
//
//                        }
//                    });
//                }
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode ==1 && resultCode == RESULT_OK && data !=null){
//            resultUri = data.getData();
//            profile_image.setImageURI(resultUri);
//        }
    }
}