package com.example.dairaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPortal extends AppCompatActivity {
    Button addCategory, addMentor, viewRegisterations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);

        getSupportActionBar().hide();

        addCategory = findViewById(R.id.addcategoriesofevents);
        addMentor = findViewById(R.id.addmentors);
        viewRegisterations = findViewById(R.id.viewregisteraion);
    }

    public void Add_Category(View view) {
        Intent intent = new Intent(this, AddEventCategory.class);
        startActivity(intent);
    }

    public void Add_Mentors(View view) {
        Intent intent = new Intent(this, AddMentor.class);
        startActivity(intent);
    }

    public void View_Registeration(View view) {
        Intent intent = new Intent(this, ViewAllRegisterations.class);
        startActivity(intent);
    }
}