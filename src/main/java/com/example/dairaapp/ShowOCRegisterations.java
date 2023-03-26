package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowOCRegisterations extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ShowRegisteration> registeredLists;
    ArrayList<String> eventList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAdapter adapter;
    String OCEvent,OCSubEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ocregisterations);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        OCEvent = intent.getStringExtra("OCEvent");
        OCSubEvent = intent.getStringExtra("OCSubEvent");

        recyclerView = findViewById(R.id.eventshowrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        registeredLists = new ArrayList<>();

        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");
        LoadDataFromFirebase();
    }

    private void LoadDataFromFirebase() {
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    if (snapshot.getKey().equals("Participants")) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {//ali
                            for (DataSnapshot snapshot2: snapshot1.getChildren()){//subevents
                                //String Sub_Event = snapshot2.child("SubEvents").toString();
                                if(snapshot2.getKey().equals("SubEvents")){
                                    for (DataSnapshot snapshot3: snapshot2.getChildren()){
                                        if (snapshot3.getKey().equals(OCSubEvent)){
                                            ShowRegisteration event = new ShowRegisteration();
                                            event.setName(snapshot1.child("Name").getValue(String.class));
                                            event.setEmail(snapshot1.child("Email").getValue(String.class));
                                            event.setAmbassador(snapshot1.child("Ambassador").getValue(String.class));
                                            event.setCnic(snapshot1.child("CNIC").getValue(String.class));
                                            event.setContact(snapshot1.child("Contact").getValue(String.class));
                                            registeredLists.add(event);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                adapter = new FirebaseAdapter(registeredLists, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void clear(){
        if(registeredLists!= null){
            registeredLists.clear();
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
        else{
            registeredLists = new ArrayList<>();
        }
    }
}