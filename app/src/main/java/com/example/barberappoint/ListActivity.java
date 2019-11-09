package com.example.barberappoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Appointment> appoint;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    Button btnAdd;
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnAdd){
            Intent intent = new Intent(this, InsertActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.btnLogout){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUtil.attachListener();
                        }
                    });
            FirebaseUtil.detachListener();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnAdd = findViewById(R.id.btnAdd);
        btnLogout = findViewById(R.id.btnLogout);
        FirebaseUtil.openFbReference("appointment", this);
        RecyclerView rvAppointments = findViewById(R.id.rvAppointments);
        final AppointmentAdapter adapter = new AppointmentAdapter();
        rvAppointments.setAdapter(adapter);
        LinearLayoutManager appointmentsLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAppointments.setLayoutManager(appointmentsLayoutManager);

        btnAdd.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        FirebaseUtil.attachListener();
    }
}
