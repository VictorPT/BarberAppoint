package com.example.barberappoint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>{
    ArrayList<Appointment> appoints;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    public AppointmentAdapter(){

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        appoints = FirebaseUtil.mAppoint;
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Appointment ap = dataSnapshot.getValue(Appointment.class);
                ap.setId(dataSnapshot.getKey());
                appoints.add(ap);
                notifyItemInserted(appoints.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildListener);
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.rv_row, parent, false);
        return  new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appoint = appoints.get(position);
        holder.bind(appoint);
    }

    @Override
    public int getItemCount() {
        return appoints.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        TextView tvName;
        TextView tvBarber;
        TextView tvService;
        TextView tvPrice;
        TextView tvDate;


        public AppointmentViewHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvBarber = itemView.findViewById(R.id.tvBarber);
            tvService = itemView.findViewById(R.id.tvService);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDate = itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Appointment appoints){
            tvName.setText(appoints.getName());
            tvBarber.setText(appoints.getBarber());
            tvService.setText(appoints.getService());
            tvPrice.setText(appoints.getPrice());
            tvDate.setText(appoints.getDate()+" - "+ appoints.getHour());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Appointment selectedAppoint = appoints.get(position);
            Intent intent = new Intent(view.getContext(), InsertActivity.class);
            intent.putExtra("Appoint", selectedAppoint);
            view.getContext().startActivity(intent);
        }
    }
}
