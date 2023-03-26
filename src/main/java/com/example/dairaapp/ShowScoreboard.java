package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowScoreboard extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView1,textView2,textView3;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Events> showEventscore;
    ArrayList<String> eventsList,subeventsList;
    String selectedEvent,selectedSubEvent,selectedStatus;
    String pusername,pemail;
    FirebaseEventAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scoreboard);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.regeventrecyclerView);
        textView1 = findViewById(R.id.textViewfirstpos);
        textView2 = findViewById(R.id.textViewsecondpos);
        textView3 = findViewById(R.id.textViewthirdpos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        showEventscore = new ArrayList<>();
        eventsList = new ArrayList<>();
        subeventsList = new ArrayList<>();
        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");

        Intent i = getIntent();
        pusername = i.getStringExtra("Username");
        pemail = i.getStringExtra("Email");
        loadScoreboard();
    }

    private void loadScoreboard() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()) {
                    if (snapshot.getKey().equals("Participants")) {
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            if(snapshot1.getKey().equals(pusername)){
                                if (snapshot1.child("Email").getValue(String.class).equals(pemail)){
                                    for (DataSnapshot snapshot2: snapshot1.child("SubEvents").getChildren()) {
                                        Log.d("tag","found user");
                                        String se = snapshot2.getKey();
                                        String e = snapshot2.getValue(String.class);
                                        eventsList.add(e);
                                        subeventsList.add(se);
                                    }
                                }
                            }
                        }
                    }
                }

                Log.d("tag", "eventsList"+eventsList);
                Log.d("tag", "subeventList"+subeventsList);

                for (DataSnapshot snapshot: datasnapshot.getChildren()) {
                    if (snapshot.getKey().equals("Event")){
                        for (DataSnapshot snapshot1: snapshot.getChildren()){ //social, business
                            if(!snapshot1.getKey().equals("Social")){
                                if(eventsList.contains(snapshot1.getKey().toString())){
                                    for (DataSnapshot snapshot2: snapshot1.getChildren()){
                                        if(subeventsList.contains(snapshot2.getKey())){
                                            Events event = new Events();
                                            event.setEvent(snapshot1.getKey());
                                            event.setSubevent(snapshot2.getKey());
                                            event.setInfo(snapshot2.child("Status").getValue(String.class));
                                            showEventscore.add(event);
                                        }
                                    }
                                    adapter = new FirebaseEventAdapter(showEventscore, getApplicationContext(), new FirebaseEventAdapter.ItemClickListener() {
                                        @Override
                                        public void onItemClick(Events event) {
                                            selectedEvent = event.getEvent();
                                            selectedSubEvent = event.getSubevent();
                                            selectedStatus = event.getInfo();
                                            Toast.makeText(ShowScoreboard.this, ""+selectedSubEvent+selectedStatus, Toast.LENGTH_SHORT).show();
                                            if(selectedStatus.equals("Held")){
                                                textView1.setText(snapshot1.child(selectedSubEvent).child("First").getValue(String.class));
                                                textView2.setText(snapshot1.child(selectedSubEvent).child("Second").getValue(String.class));
                                                textView3.setText(snapshot1.child(selectedSubEvent).child("Third").getValue(String.class));
                                            }else{
                                                textView1.setText("Yet to be held");
                                                textView2.setText("Yet to be held");
                                                textView3.setText("Yet to be held");
                                            }
                                        }
                                    });
                                    recyclerView.setAdapter(adapter);
                                    Log.d("tag","adapter set");
                                    adapter.notifyDataSetChanged();
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
    public void clear(){
        if(showEventscore!= null){
            showEventscore.clear();
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
        else{
            showEventscore = new ArrayList<>();
        }
    }
}