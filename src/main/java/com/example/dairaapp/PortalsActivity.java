package com.example.dairaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PortalsActivity extends AppCompatActivity {
    ImageView dairaLogo;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portals);
        getSupportActionBar().hide();
        creatingDataBase();
    }
    public void Admin_Login(View view) {
        Intent intent = new Intent(this, Admin_Login.class);
        startActivity(intent);
    }

    public void Mentor_Login(View view) {
        Intent intent = new Intent(this, Mentor_Login.class);
        startActivity(intent);
    }

    public void OC_Login(View view) {
        Intent intent = new Intent(this, OC_Login.class);
        startActivity(intent);
    }

    public void Participant_Login(View view) {
        Intent intent = new Intent(this, ParticipantSignUp.class);
        startActivity(intent);
    }


    public void creatingDataBase() {
        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");

        //Information of admin
        reference.child("Admin")
                .child("Admin1")
                .child("Email").setValue("mahrukh@nu.edu.pk");
        reference.child("Admin")
                .child("Admin1")
                .child("Password").setValue("mahrukh");

        reference.child("Admin")
                .child("Admin2")
                .child("Email").setValue("hira@nu.edu.pk");
        reference.child("Admin")
                .child("Admin2")
                .child("Password").setValue("hira");

        Toast.makeText(this, "Database created", Toast.LENGTH_SHORT).show();
        Log.d("tag", "db created");
    }
}