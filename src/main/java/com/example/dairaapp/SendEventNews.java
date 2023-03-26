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

public class SendEventNews extends AppCompatActivity {
    EditText editText;
    TextView textView1,textView2;
    String OCEvent,OCSubEvent;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_event_news);
        getSupportActionBar().hide();
        editText = findViewById(R.id.edittextscorethree);
        textView1 = findViewById(R.id.textView13);
        textView2 = findViewById(R.id.textView16);
        Intent intent = getIntent();
        OCEvent = intent.getStringExtra("OCEvent");
        OCSubEvent = intent.getStringExtra("OCSubEvent");

        textView1.setText(OCEvent);
        textView2.setText(OCSubEvent);
        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");
    }

    public void SendNewsfunc(View view) {
        String news= editText.getText().toString();
        if (news == "") {
            Toast.makeText(this, "Enter news first", Toast.LENGTH_SHORT).show();
        } else {
            reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child("Event").child(OCEvent).child(OCSubEvent).child("News").setValue(news);
                    Toast.makeText(SendEventNews.this, "News set", Toast.LENGTH_SHORT).show();
                    Log.d("tag", "News set");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}