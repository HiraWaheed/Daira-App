package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddMentor extends AppCompatActivity {
    ConstraintLayout cl;
    EditText username,email, password, event;
    Button addbtn;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentor);
        getSupportActionBar().hide();

        cl = findViewById(R.id.memberregisteration);
        username = cl.findViewById(R.id.inputusername);
        email = cl.findViewById(R.id.inputemail);
        password = cl.findViewById(R.id.inputpassword);
        event = cl.findViewById(R.id.inputevent);
        addbtn = cl.findViewById(R.id.addmember);

        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
    }

    private void addData() {
        String Username = username.getText().toString(); //uniqueness check?
        reference.addValueEventListener(new ValueEventListener() {

            String Email = email.getText().toString();
            String Password = password.getText().toString();
            String Event = event.getText().toString();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reference.child("Mentor").child(Username).child("Email").setValue(Email);
                reference.child("Mentor").child(Username).child("Password").setValue(Password);
                reference.child("Mentor").child(Username).child("Event").setValue(Event);
                Toast.makeText(AddMentor.this, "Mentor Added", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}