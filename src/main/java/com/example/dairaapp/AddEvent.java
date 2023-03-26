package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddEvent extends AppCompatActivity implements View.OnClickListener{
    TextView Category;
    EditText subEvent, desc, url;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button addDataBtn;
    ArrayList<String> eventList = new ArrayList<>();
    String getMentorName, CategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().hide();
        Category = findViewById(R.id.categorytxt);
        subEvent = findViewById(R.id.inputsubeventmentor);
        desc = findViewById(R.id.description);
        url = findViewById(R.id.imgurl);
        addDataBtn = findViewById(R.id.eventdetailbtn);

        Intent intent = getIntent();
        getMentorName = intent.getStringExtra("MentorName");
        Log.d("tag", getMentorName);

        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");

        addDataBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        LoadDatafromFirebase();
    }

    private void LoadDatafromFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            String SubEvent = subEvent.getText().toString();
            String Descriptioon = desc.getText().toString();
            String URL = url.getText().toString();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    if (snapshot.getKey().equals("Mentor")){
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            if (snapshot1.child("Email").getValue().toString().equals(getMentorName)){
                                CategoryName = snapshot1.child("Event").getValue().toString();
                                Log.d("tag", CategoryName);
                                Category.setText(CategoryName);
                            }
                        }
                    }
                }

                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    if (snapshot.getKey().equals("Event")){
                        for (DataSnapshot snapshot1: snapshot.getChildren()){ //social, business
                            Log.d("tag", "Event"+CategoryName+" sub event"+snapshot1.getKey());
                            if (snapshot1.getKey().equals(CategoryName)){
                                for (DataSnapshot snapshot2: snapshot1.getChildren()){
                                    Log.d("tag", "Snap2"+snapshot2.getKey()); //concert mushaira, theme dinner
                                    //String Subevent = reference.child("Event").child(CategoryName).child(SubEvent);
                                    String Des = reference.child("Event").child(CategoryName).child(SubEvent).child("Description").setValue(Descriptioon).toString();
                                    String ImageUrl = reference.child("Event").child(CategoryName).child(SubEvent).child("URL").setValue(URL).toString();
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