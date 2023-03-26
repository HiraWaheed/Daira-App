package com.example.dairaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MentorPortal extends AppCompatActivity {
    String MentorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_portal);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        MentorName = intent.getStringExtra("MentorName");
    }
    public void Add_Event(View view) {
        Intent intent = new Intent(this, AddEvent.class);
        intent.putExtra("MentorName", MentorName);
        startActivity(intent);
    }

    public void Add_OC(View view) {
        Intent intent = new Intent(this, AddOC.class);
        startActivity(intent);
    }

    public void View_Event_Registeration(View view) {
        Intent intent = new Intent(this, ShowEventRegisteration.class);
        intent.putExtra("MentorName", MentorName);
        startActivity(intent);
    }

    public void View_All_Registeration(View view) {
        Intent intent = new Intent(this, ViewAllRegisterations.class);
        startActivity(intent);
    }

    public void SendMessagge(View view) {
        Intent intent = new Intent(this, SendMessageToOC.class);
        intent.putExtra("MentorName", MentorName);
        startActivity(intent);
    }
}