package com.example.bookrides.RegisterVehicle;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookrides.Model.User;
import com.example.bookrides.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Suzuki extends AppCompatActivity {



    private CircleImageView profile_image;

    private EditText registerFullName,registerIdNumber,
            registerPhoneNumber, registerEmail, registerPassword, registerLocation;

    private Spinner bloodGroupsSpinner;

    private Button registerButton;

    private Uri ImageUri;

    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    private FirebaseStorage firebasestorage;
    FirebaseDatabase database;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_suzuki);

        database = FirebaseDatabase.getInstance();
        firebasestorage = FirebaseStorage.getInstance();

        profile_image = findViewById(R.id.profile_image);
        registerFullName = findViewById(R.id.registerFullName);
        registerIdNumber = findViewById(R.id.registerIdNumber);
        registerPhoneNumber = findViewById(R.id.registerPhoneNumber);

        bloodGroupsSpinner = findViewById(R.id.vehicleTypeSpinner);
        registerLocation = findViewById(R.id.registerLocation);
        registerButton = findViewById(R.id.registerButton);
        relativeLayout = findViewById(R.id.relativelayout1);
        loader = new ProgressDialog(this);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpoadImage();
                relativeLayout.setVisibility(View.VISIBLE);
//                profile_image.setVisibility(View.GONE);

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final StorageReference reference = firebasestorage.getReference().child("Suzuki")
                        .child(System.currentTimeMillis()+"");
                reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                User suzki = new User();
                                suzki.setProfilepictureurl(uri.toString());

                                suzki.setName(registerFullName.getText().toString());
                                suzki.setPhonenumber(registerPhoneNumber.getText().toString());
                                suzki.setPhonenumber1(registerPhoneNumber.getText().toString());
                                suzki.setLocation(registerLocation.getText().toString());
                                suzki.setBloodgroup(bloodGroupsSpinner.getSelectedItem().toString());
                                suzki.setIdnumber(registerIdNumber.getText().toString());
                                suzki.setType("Suzuki");

                                database.getReference().child("Suzuki").push().setValue(suzki).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Register_Suzuki.this, "Vehicle register Succesfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Register_Suzuki.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                });
            }
        });

    }

    private void UpoadImage() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent= new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent,101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(Register_Suzuki.this, "Permission denied ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode == RESULT_OK){
            ImageUri = data.getData();
            profile_image.setImageURI(ImageUri);

        }
    }
}