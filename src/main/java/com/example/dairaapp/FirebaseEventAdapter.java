package com.example.dairaapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirebaseEventAdapter extends RecyclerView.Adapter<FirebaseEventAdapter.ViewHolder>{
    ArrayList<Events> eventslist;
    Context context;
    private ItemClickListener mItemListener;
    public FirebaseEventAdapter(ArrayList<Events> eventslist, Context context,ItemClickListener itemClickListener) {
        this.eventslist = eventslist;
        this.context = context;
        this.mItemListener = itemClickListener;
    }

    @NonNull
    @Override
    public FirebaseEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_events_layout, parent, false);
        return new FirebaseEventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseEventAdapter.ViewHolder holder, int position) {
        holder.event.setText(eventslist.get(position).getEvent());
        holder.subevent.setText(eventslist.get(position).getSubevent());
        holder.info.setText(eventslist.get(position).getInfo());
        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(eventslist.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return eventslist.size();
    }
    public interface ItemClickListener{
        void onItemClick(Events event);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView event, subevent, info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            event = itemView.findViewById(R.id.txtrecyclerviewevent);
            subevent = itemView.findViewById(R.id.txtrecyclerviewsubevent);
            info = itemView.findViewById(R.id.txtrecyclerviewinfo);
        }
    }
}
