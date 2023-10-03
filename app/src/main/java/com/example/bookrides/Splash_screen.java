package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(4000);
                }
                catch (Exception e){
                e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(Splash_screen.this,Welcome_Activity.class);
                    startActivity(intent);
                }
            }
        };thread.start();
    }
}