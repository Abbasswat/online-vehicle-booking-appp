package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookrides.databinding.ActivityForgetBinding;

public class ForgetActivity extends AppCompatActivity {

    ActivityForgetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnForgetPassword.setOnClickListener(view -> {
            onBackPressed();
        });


    }
}