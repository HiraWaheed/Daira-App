package com.example.dairaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    ImageView dairaLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //19F-0103 19F-0166
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        dairaLogo = findViewById(R.id.dairalogo);
        handler = new Handler();
        dairaLogo.setImageResource(R.drawable.daira_logo);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, PortalsActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}