package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowEventNews extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView1;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Events> showEventNews;
    ArrayList<String> eventsList,subeventsList;
    String selectedEvent,selectedSubEvent,selectedNews;
    String pusername,pemail;
    FirebaseEventAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_news);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.regeventrecyclerView);
        textView1 = findViewById(R.id.textViewfirstpos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        showEventNews = new ArrayList<>();
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
                    Log.d("tag","line 61");
                    if (snapshot.getKey().equals("Participants")) {
                        Log.d("tag","line 63");
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            if(snapshot1.getKey().equals(pusername)){
                                Log.d("tag","line 66");
                                if (snapshot1.child("Email").getValue(String.class).equals(pemail)){
                                    Log.d("tag","line 68");
                                    for (DataSnapshot snapshot2: snapshot1.child("SubEvents").getChildren()) {
                                        Log.d("tag","found user");
                                        String se = snapshot2.getKey();
                                        String e = snapshot2.getValue(String.class);
                                        Log.d("tag",""+e+se);
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
                                if(eventsList.contains(snapshot1.getKey().toString())){
                                    for (DataSnapshot snapshot2: snapshot1.getChildren()){
                                        if(subeventsList.contains(snapshot2.getKey())){
                                            Events event = new Events();
                                            event.setEvent(snapshot1.getKey());
                                            event.setSubevent(snapshot2.getKey());
                                            event.setInfo(snapshot2.child("Status").getValue(String.class));
                                            showEventNews.add(event);
                                        }
                                    }
                                    adapter = new FirebaseEventAdapter(showEventNews, getApplicationContext(), new FirebaseEventAdapter.ItemClickListener() {
                                        @Override
                                        public void onItemClick(Events event) {
                                            selectedEvent = event.getEvent();
                                            selectedSubEvent = event.getSubevent();
                                            selectedNews = snapshot1.child(selectedSubEvent).child("Description").getValue(String.class);
                                            textView1.setText(selectedNews);
                                            Toast.makeText(ShowEventNews.this, ""+selectedNews, Toast.LENGTH_SHORT).show();
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void clear(){
        if(showEventNews!= null){
            showEventNews.clear();
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
        else{
            showEventNews = new ArrayList<>();
        }
    }
}