package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowEventRegisteration extends AppCompatActivity {
    String getMentorName, eventName;
    TextView CategoryName;
    RecyclerView recyclerView;
    ArrayList<ShowRegisteration> eventLists;
    ArrayList<String> eventList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_registeration);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        getMentorName = intent.getStringExtra("MentorName");

        CategoryName = findViewById(R.id.eventcategoryname);
        recyclerView = findViewById(R.id.eventshowrecyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        eventLists = new ArrayList<>();

        database = FirebaseDatabase.getInstance("https://daira22app-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");

        LoadDataFromFirebase();
    }
    public void LoadDataFromFirebase(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                clear();
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    if (snapshot.getKey().equals("Mentor")){
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            if (snapshot1.child("Email").getValue().toString().equals(getMentorName)){
                                eventName = snapshot1.child("Event").getValue().toString();
                                Log.d("tag", eventName);
                                CategoryName.setText(eventName);
                            }
                        }
                    }
                }

                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    if (snapshot.getKey().equals("Event")){
                        for (DataSnapshot snapshot1: snapshot.getChildren()){ //social, business
                            Log.d("tag", "Event"+eventName+" sub event"+snapshot1.getKey());
                            if (snapshot1.getKey().equals(eventName)){
                                for (DataSnapshot snapshot2: snapshot1.getChildren()){
                                    Log.d("tag", "Snap2"+snapshot2.getKey());
                                    //eventList.add(snapshot2.getKey());
                                }
                            }
                        }
                    }
                }

                Log.d("tag", "eventList"+eventList);

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    if (snapshot.getKey().equals("Participants")) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            for (DataSnapshot snapshot2: snapshot1.getChildren()){
                                //String Sub_Event = snapshot2.child("SubEvents").toString();
                                for (DataSnapshot snapshot3: snapshot2.getChildren()){
                                    if (snapshot3.getValue().toString().equals(eventName)){
                                        ShowRegisteration event = new ShowRegisteration();
                                        event.setName(snapshot1.child("Name").getValue().toString());
                                        event.setEmail(snapshot1.child("Email").getValue().toString());
                                        event.setAmbassador(snapshot1.child("Ambassador").getValue().toString());
                                        event.setCnic(snapshot1.child("CNIC").getValue().toString());
                                        event.setContact(snapshot1.child("Contact").getValue().toString());
                                        Log.d("tag", eventLists.toString());
                                        eventLists.add(event);
                                    }
                                }
                            }
                        }
                    }
                }
                adapter = new FirebaseAdapter(eventLists, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void clear(){
        if(eventLists!= null){
            eventLists.clear();
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
        else{
            eventLists = new ArrayList<>();
        }
    }
}