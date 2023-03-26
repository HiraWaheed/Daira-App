package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendMessageToOC extends AppCompatActivity {
    EditText messageText;
    String getMentorName, eventName;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button sendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message_to_oc);
        getSupportActionBar().hide();

        messageText = findViewById(R.id.messagetxt);
        sendMessageButton = findViewById(R.id.sendmessagebtn);
        Intent intent = getIntent();
        getMentorName = intent.getStringExtra("MentorName");

        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadDataFromFirebase();
            }
        });
    }
    private void LoadDataFromFirebase() {
        String MentorMessage = messageText.getText().toString();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    if (snapshot.getKey().equals("Mentor")){
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            if (snapshot1.child("Email").getValue().toString().equals(getMentorName)){
                                eventName = snapshot1.child("Event").getValue().toString();
                                Log.d("tag", eventName);
                            }
                        }
                    }
                }


                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    if (snapshot.getKey().equals("OC")){
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            if (snapshot1.child("OCEvent").getValue().toString().equals(eventName)){
                                reference.child("OC").child(snapshot1.getKey()).child("SendMessage").setValue(MentorMessage);
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