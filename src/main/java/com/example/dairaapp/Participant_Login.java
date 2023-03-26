package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Participant_Login extends AppCompatActivity {
    ConstraintLayout cl;
    EditText username, password;
    Button loginButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_login);
        getSupportActionBar().hide();

        cl = findViewById(R.id.loginlayout);
        username = cl.findViewById(R.id.edttxtname);
        password = cl.findViewById(R.id.edttxtpassword);
        loginButton = cl.findViewById(R.id.loginbtn);

        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validUserName() | !validatePassword()){
                    Log.d("tag", " not successful");
                    Toast.makeText(Participant_Login.this, "Not Valid User", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    isValid();
                }
            }
        });

    }

    public void GoToParticipantSignUp(View view) {
        Intent intent = new Intent(Participant_Login.this, ParticipantSignUp.class);
        startActivity(intent);
    }

    private boolean validUserName() {
        String val = username.getText().toString();
        if (val.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getText().toString();
        if (val.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }
    private void isValid() {
        final String Username = username.getText().toString();
        final String Password = password.getText().toString();
        Log.d("tag", "username:" + Username + " Password:" + Password);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()) {
                    if (snapshot.getKey().equals("Participants")) {
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            if(snapshot1.getKey().equals(Username)){
                                Log.d("tag","username matched");
                                String dbpassword = snapshot1.child("Password").getValue(String.class);
                                if (Password.equals(dbpassword)){
                                    Log.d("tag", "Valid User");
//                                    Toast.makeText(Participant_Login.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Participant_Login.this,ParticipantsPortal.class);
                                    i.putExtra("Username",Username);
                                    i.putExtra("Email",snapshot1.child("Email").getValue(String.class));
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(Participant_Login.this, "Invalid Username or password", Toast.LENGTH_SHORT).show();
                                    Log.d("tag", "Username or Password not matched");
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}