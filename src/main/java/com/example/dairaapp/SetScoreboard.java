package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetScoreboard extends AppCompatActivity {
    EditText editText1,editText2,editText3;
    TextView textView1,textView2;
    String OCEvent,OCSubEvent;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_scoreboard);
        getSupportActionBar().hide();
        editText1 = findViewById(R.id.edittextscoreone);
        editText2 = findViewById(R.id.edittextscoretwo);
        editText3 = findViewById(R.id.edittextscorethree);
        textView1 = findViewById(R.id.textView13);
        textView2 = findViewById(R.id.textView16);
        Intent intent = getIntent();
        OCEvent = intent.getStringExtra("OCEvent");
        OCSubEvent = intent.getStringExtra("OCSubEvent");
        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");

        editText1.setText("NA");
        editText2.setText("NA");
        editText3.setText("NA");

        textView1.setText(OCEvent);
        textView2.setText(OCSubEvent);
    }

    public void SetEventScorefunc(View view) {
        String score1 = editText1.getText().toString();
        String score2 = editText2.getText().toString();
        String score3 = editText3.getText().toString();
        if (score1 == "NA") {
            Toast.makeText(this, "Enter scores first", Toast.LENGTH_SHORT).show();
        } else {
            reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.child("Event").child(OCEvent).equals("Social")){
                        if(snapshot.child("Event").child(OCEvent).child(OCSubEvent).child("Status").equals("Held")){
                            reference.child("Event").child(OCEvent).child(OCSubEvent).child("First").setValue(score1);
                            reference.child("Event").child(OCEvent).child(OCSubEvent).child("Second").setValue(score2);
                            reference.child("Event").child(OCEvent).child(OCSubEvent).child("Third").setValue(score3);
                            Toast.makeText(SetScoreboard.this, "scores set", Toast.LENGTH_SHORT).show();
                            Log.d("tag", "scores set");
                        } else{
                            Toast.makeText(SetScoreboard.this, "Event not held yet", Toast.LENGTH_SHORT).show();
                            Log.d("tag","Event not held yet");
                        }
                    }else{
                        Toast.makeText(SetScoreboard.this, "Social Events have no scoreboard", Toast.LENGTH_SHORT).show();
                        Log.d("tag","Social Events have no scoreboard");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}