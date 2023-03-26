package com.example.dairaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class OCPortal extends AppCompatActivity {
    String OCEvent,OCSubEvent,OCMessage;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocportal);
        getSupportActionBar().hide();
        textView = findViewById(R.id.textViewmsg);
        Intent intent = getIntent();
        OCEvent = intent.getStringExtra("OCEvent");
        OCSubEvent = intent.getStringExtra("OCSubEvent");
        OCMessage = intent.getStringExtra("OCMessage");
        textView.setText(OCMessage);
    }

    public void ViewRegisteredParticipants(View view) {
        Intent intent1 = new Intent(OCPortal.this,ShowOCRegisterations.class);
        intent1.putExtra("OCEvent",OCEvent);
        intent1.putExtra("OCSubEvent",OCSubEvent);
        startActivity(intent1);
    }

    public void SetEventVenuefunc(View view) {
        Intent intent1 = new Intent(OCPortal.this,SetEventVenue.class);
        intent1.putExtra("OCEvent",OCEvent);
        intent1.putExtra("OCSubEvent",OCSubEvent);
        startActivity(intent1);
    }

    public void SendEventNewsfunc(View view) {
        Intent intent1 = new Intent(OCPortal.this,SendEventNews.class);
        intent1.putExtra("OCEvent",OCEvent);
        intent1.putExtra("OCSubEvent",OCSubEvent);
        startActivity(intent1);
    }

    public void SetScoreboardfunc(View view) {
        Intent intent1 = new Intent(OCPortal.this,SetScoreboard.class);
        intent1.putExtra("OCEvent",OCEvent);
        intent1.putExtra("OCSubEvent",OCSubEvent);
        startActivity(intent1);
    }
}