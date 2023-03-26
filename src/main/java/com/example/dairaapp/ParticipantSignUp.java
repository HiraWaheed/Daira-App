package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParticipantSignUp extends AppCompatActivity {
    EditText name, cnic, contact, email, password, code;
    RadioGroup radiogroupstatus, radiogroupgender;
    RadioButton genderbtn, statusbtn;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_signup);
        getSupportActionBar().hide();

        name = findViewById(R.id.edittxtname);
        cnic = findViewById(R.id.edittxtcnic);
        contact = findViewById(R.id.edittxtcontact);
        email = findViewById(R.id.edittxtemail);
        password = findViewById(R.id.edittxtpassword);
        code = findViewById(R.id.edittxtcode);
        radiogroupstatus = findViewById(R.id.radioGroup);
        radiogroupgender = findViewById(R.id.radioGroupgender);

        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");
    }

    public void GoToParticipantLogin(View view) {
//        Toast.makeText(this, "login clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ParticipantSignUp.this, Participant_Login.class);
        startActivity(intent);
    }

    public void AddParticipant(View view) {
        String Fullname = name.getText().toString();
        String CNIC = cnic.getText().toString();
        String Contact = contact.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String AmbCode = code.getText().toString();
        String Status = statusbtn.getText().toString();
        String Gender = genderbtn.getText().toString();
        if(Status.equals("Private")){
           AmbCode = "Null";
        }
        String finalAmbCode = AmbCode;
        Log.d("tag","add participant 67");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reference.child("Participants").child(Fullname).child("CNIC").setValue(CNIC);
                reference.child("Participants").child(Fullname).child("Contact").setValue(Contact);
                reference.child("Participants").child(Fullname).child("Email").setValue(Email);
                reference.child("Participants").child(Fullname).child("Gender").setValue(Gender);
                reference.child("Participants").child(Fullname).child("Password").setValue(Password);
                reference.child("Participants").child(Fullname).child(Status).setValue(finalAmbCode);
                Toast.makeText(ParticipantSignUp.this, "Participant signed up", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = new Intent(ParticipantSignUp.this, Participant_Login.class);
        startActivity(intent);
    }

    public void checkradiostatusButton(View view) {
        int radioId = radiogroupstatus.getCheckedRadioButtonId();
        statusbtn = findViewById(radioId);
        String Status = statusbtn.getText().toString();
        Toast.makeText(this, "status:" + Status, Toast.LENGTH_SHORT).show();
    }

    public void checkradiogenderbutton(View view) {
        int radiogId = radiogroupgender.getCheckedRadioButtonId();
        genderbtn = findViewById(radiogId);
        String Gender = genderbtn.getText().toString();
        Toast.makeText(this, "status:" + Gender, Toast.LENGTH_SHORT).show();
    }
}