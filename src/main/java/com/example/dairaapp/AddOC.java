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

public class AddOC extends AppCompatActivity {
    ConstraintLayout cl;
    EditText username,email, password, subevent;
    Button addbtn;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oc);

        cl = findViewById(R.id.memberregisteration);
        username = cl.findViewById(R.id.inputusername);
        email = cl.findViewById(R.id.inputemail);
        password = cl.findViewById(R.id.inputpassword);
        subevent = cl.findViewById(R.id.inputevent);
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
            String SubEvent = subevent.getText().toString();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reference.child("OC").child(Username).child("Email").setValue(Email);
                reference.child("OC").child(Username).child("Password").setValue(Password);
                reference.child("OC").child(Username).child("SubEvent").setValue(SubEvent);
                Toast.makeText(AddOC.this, "OC member Added", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}