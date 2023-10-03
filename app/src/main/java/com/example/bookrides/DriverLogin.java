package com.example.bookrides;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverLogin extends AppCompatActivity {
    private MaterialButton backButton;

    private EditText loginEmail, loginPassword;
    private TextView forgotPassword;
    private Button loginButton;

    private ProgressDialog loader;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener authStateListener;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        mAuth = FirebaseAuth.getInstance();



        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String userType = dataSnapshot.child("type").getValue(String.class);
                                if ("driver".equals(userType)) {
                                    // User is a driver, redirect to driver dashboard
                                    Intent intent = new Intent(DriverLogin.this, Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                } else  {
                                    // User is a customer, do nothing or show a message
                                    // You can also redirect to a customer dashboard if you have one
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error if needed
                        }
                    });
                }
            }
        };
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = mAuth.getCurrentUser();
//                if (user !=null){
//                    Intent intent = new Intent(DriverLogin.this, Dashboard.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };


        backButton = findViewById(R.id.btn_DriverSignUp);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        forgotPassword = findViewById(R.id.txt_ForgetPassword);
        loginButton = findViewById(R.id.loginButton);

        loader = new ProgressDialog(this);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverLogin.this,  RegisterDriver.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String email = loginEmail.getText().toString().trim();
                final  String password = loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    loginEmail.setError("Email is required!");
                }
                if(TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is required!");
                }

                else {
                    loader.setMessage("Log in in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(DriverLogin.this,
                                                "Log in successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DriverLogin.this, Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(DriverLogin.this,
                                                task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                    loader.dismiss();
                                }
                            });
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}