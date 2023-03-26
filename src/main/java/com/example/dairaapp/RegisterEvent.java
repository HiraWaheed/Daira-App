package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterEvent extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    String selectedEvent,selectedSubEvent;
    String pusername,pemail;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Events> showEvents;
    FirebaseEventAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.regeventrecyclerView);
        textView = findViewById(R.id.textViewfirstpos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        showEvents = new ArrayList<>();
        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");

        Intent i = getIntent();
        pusername = i.getStringExtra("Username");
        pemail = i.getStringExtra("Email");
        loadEventslist();
    }

    private void loadEventslist() {
        Log.d("tag", "func line 48");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Log.d("tag", "func line 52");
                clear();
                for (DataSnapshot snapshot: datasnapshot.getChildren()) {
                    if (snapshot.getKey().equals("Event")){
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            for (DataSnapshot snapshot2: snapshot1.getChildren()){
                                Events event = new Events();
                                String eName = snapshot1.getKey();
                                Log.d("tag","Event is:"+eName);
                                String seName = snapshot2.getKey();
                                Log.d("tag","SubEvent is:"+seName);
                                event.setEvent(eName);
                                event.setSubevent(seName);
                                String evenue = snapshot2.child("Venue").getValue().toString();
                                event.setInfo(evenue);
                                showEvents.add(event);
                            }
                            adapter = new FirebaseEventAdapter(showEvents, getApplicationContext(), new FirebaseEventAdapter.ItemClickListener() {
                                @Override
                                public void onItemClick(Events event) {
                                    selectedEvent = event.getEvent();
                                    selectedSubEvent = event.getSubevent();
                                    Toast.makeText(RegisterEvent.this, ""+event.getSubevent(), Toast.LENGTH_SHORT).show();
                                    textView.setText(selectedSubEvent);
                                }
                            });
                            recyclerView.setAdapter(adapter);
                            Log.d("tag","adapter set");
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void clear(){
        if (showEvents != null){
            showEvents.clear();
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
        else{
            showEvents = new ArrayList<>();
        }
    }
    public void registerEventfunc(View view) {
        if (textView.getText().toString() == ""){
            Toast.makeText(this, "Please select an event from the list first", Toast.LENGTH_SHORT).show();
        }else {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Participants").child(pusername).child("Email").getValue(String.class).equals(pemail)){
                        Log.d("tag","matched username and email");
                        reference.child("Participants").child(pusername).child("SubEvents").child(selectedSubEvent).setValue(selectedEvent);
                        reference.child("Participants").child(pusername).child("SubEvents").child("Mushaira").setValue("Social");
                        reference.child("Participants").child(pusername).child("SubEvents").child("Cultural Walk").setValue("Social");
                        reference.child("Participants").child(pusername).child("SubEvents").child("Theme Dinner").setValue("Social");
                        reference.child("Participants").child(pusername).child("SubEvents").child("Celebrity Talk Show").setValue("Social");
                        reference.child("Participants").child(pusername).child("SubEvents").child("Qawalli Night").setValue("Social");
                        reference.child("Participants").child(pusername).child("SubEvents").child("Book Signing").setValue("Social");
                        reference.child("Participants").child(pusername).child("SubEvents").child("Concert").setValue("Social");
                        reference.child("Participants").child(pusername).child("SubEvents").child("Auto Show").setValue("Social");
                        Toast.makeText(RegisterEvent.this, "Event Registered with free socials", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}