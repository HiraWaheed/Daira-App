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

public class OC_Login extends AppCompatActivity {
    ConstraintLayout cl;
    EditText username, password;
    Button loginButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oc_login);
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
                    Toast.makeText(OC_Login.this, "Not Valid User", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    isValid();
                }
            }
        });
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
        Log.d("tag", "username:"+Username+" Password:"+Password);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()) {
                    if (snapshot.getKey().equals("OC")) {
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            if(snapshot1.getKey().equals(Username)){
                                Log.d("tag","username matched");
                                String dbpassword = snapshot1.child("Password").getValue(String.class);
                                if (Password.equals(dbpassword)){
                                    Log.d("tag", "Valid User");
                                    String OCEvent = snapshot1.child("OCEvent").getValue(String.class);
                                    String SubEvent = snapshot1.child("SubEvent").getValue(String.class);
                                    Toast.makeText(OC_Login.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(OC_Login.this, OCPortal.class);
                                    intent.putExtra("OCEvent",OCEvent);
                                    intent.putExtra("OCSubEvent",SubEvent);
                                    intent.putExtra("OCMessage",snapshot1.child("Message").getValue(String.class));
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(OC_Login.this, "Invalid Username or password", Toast.LENGTH_SHORT).show();
                                    Log.d("tag", "Username or Password not matched");
                                }
                            }
                        }
                        Log.d("tag","oc ke under username not matched");
                    }
                    Log.d("tag","Daira ke under key not matched");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}