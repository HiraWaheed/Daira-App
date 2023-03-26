package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class AddEventCategory extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    DatabaseReference reference;
    Button addButton;
    EditText addCategpry, addEvent;
    ArrayList<String> arrayListCategory = new ArrayList<>();
    String category, event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_category);
        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");
        getSupportActionBar().hide();
        addCategpry = findViewById(R.id.addcategory);
        addEvent = findViewById(R.id.addevent);
        addButton = findViewById(R.id.addcategorybtn);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        category = addCategpry.getText().toString();
        event = addEvent.getText().toString();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (category.length() > 0){
                    if (arrayListCategory.contains(category)){
                        if (event.length() > 0){
                            reference.child("Event").child(category).child(event).setValue(event);
                            arrayListCategory.add(event);
                        }
                    }
                    else{
                        if (event.length() > 0) {
                            arrayListCategory.add(category);
                            arrayListCategory.add(event);
                            reference.child("Event").child(category).child(event).setValue(event);
                            Log.d("tag", String.valueOf(arrayListCategory));
                        }
                    }
                }
                else{
                    Toast.makeText(AddEventCategory.this, "Please add Category or Event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}