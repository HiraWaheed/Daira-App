package com.example.dairaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirebaseAdapter  extends RecyclerView.Adapter<FirebaseAdapter.ViewHolder>{
    ArrayList<ShowRegisteration> registerationsList;
    Context context;

    public FirebaseAdapter(ArrayList<ShowRegisteration> registerationList, Context context) {
        this.registerationsList = registerationList;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showregisterationlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(registerationsList.get(position).getName());
        holder.email.setText(registerationsList.get(position).getEmail());
        holder.ambassador.setText(registerationsList.get(position).getAmbassador());
        holder.cnic.setText(registerationsList.get(position).getCnic());
        holder.contact.setText(registerationsList.get(position).getContact());
    }

    @Override
    public int getItemCount() {
        return registerationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, email, ambassador, cnic, contact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.participantname);
            email = itemView.findViewById(R.id.participantemail);
            ambassador = itemView.findViewById(R.id.participantambassador);
            cnic = itemView.findViewById(R.id.participantcnic);
            contact = itemView.findViewById(R.id.participantcontact);
        }
    }
}
