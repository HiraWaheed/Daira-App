package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ShowVenues extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    ImageView imageView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Events> showEvents;
    String selectedSubEvent,selectedVenue;
    FirebaseEventAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_venues);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.regeventrecyclerView);
        textView = findViewById(R.id.textViewfirstpos);
        imageView = findViewById(R.id.imageViewvenue);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        showEvents = new ArrayList<>();
        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");
        loadVenuesList();
    }

    private void loadVenuesList() {
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
                                event.setEvent(snapshot1.getKey());
                                event.setSubevent(snapshot2.getKey());
                                event.setInfo(snapshot2.child("Venue").getValue().toString());
                                showEvents.add(event);
                            }
                            adapter = new FirebaseEventAdapter(showEvents, getApplicationContext(), new FirebaseEventAdapter.ItemClickListener() {
                                @Override
                                public void onItemClick(Events event) {
                                    selectedVenue = event.getInfo();
                                    selectedSubEvent = event.getSubevent();
                                    Toast.makeText(ShowVenues.this, ""+event.getSubevent(), Toast.LENGTH_SHORT).show();
                                    textView.setText(selectedSubEvent+" happening at "+selectedVenue);
                                    //get url from firebase ,send to service for downloading,return bitmap and show image here
                                    imageView.setImageResource(R.drawable.venue);
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
}