package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllRegisterations extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<ShowRegisteration> showRegisterations;
    FirebaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_registerations);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.showregisterationrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        showRegisterations = new ArrayList<>();
        database = FirebaseDatabase.getInstance("https://f0166-smdfinal-bca98-default-rtdb.firebaseio.com/");
        reference = database.getReference("Daira");

        LoadDataFromFirebase();
    }

        private void LoadDataFromFirebase() {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    clear();
                    for (DataSnapshot snapshot: datasnapshot.getChildren()){
                        if (snapshot.getKey().equals("Participants")){
                            for (DataSnapshot snapshot1: snapshot.getChildren()){
                                ShowRegisteration registeration = new ShowRegisteration();
                                registeration.setName(snapshot1.getKey());
                                registeration.setEmail(snapshot1.child("Email").getValue(String.class));
                                registeration.setAmbassador(snapshot1.child("Ambassador").getValue(String.class));
                                registeration.setCnic(snapshot1.child("CNIC").getValue(String.class));
                                registeration.setContact(snapshot1.child("Contact").getValue(String.class));
                                showRegisterations.add(registeration);
                            }
                            adapter = new FirebaseAdapter(showRegisterations, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        public void clear(){
            if (showRegisterations != null){
                showRegisterations.clear();
                if (adapter != null){
                    adapter.notifyDataSetChanged();
                }
            }
            else{
                showRegisterations = new ArrayList<>();
            }
        }
}