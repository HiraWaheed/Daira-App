package com.example.dairaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ParticipantsPortal extends AppCompatActivity {
    String pusername,pemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_portal);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        pusername = intent.getStringExtra("Username");
        pemail = intent.getStringExtra("Email");
    }

    public void GoToRegisterEvent(View view) {
        Intent intent1 = new Intent(this,RegisterEvent.class);
        intent1.putExtra("Username",pusername);
        intent1.putExtra("Email",pemail);
        startActivity(intent1);
    }

    public void GoToSeeVenue(View view) {
        Intent intent2 = new Intent(this,ShowVenues.class);
        startActivity(intent2);
    }

    public void GoToSeeScoreboard(View view) {
        Intent intent3 = new Intent(this,ShowScoreboard.class);
        intent3.putExtra("Username",pusername);
        intent3.putExtra("Email",pemail);
        startActivity(intent3);
    }

    public void GoToEventNews(View view) {
        Intent intent1 = new Intent(this,ShowEventNews.class);
        intent1.putExtra("Username",pusername);
        intent1.putExtra("Email",pemail);
        startActivity(intent1);
    }
}