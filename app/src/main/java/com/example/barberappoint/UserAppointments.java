package com.example.barberappoint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointments);

        FirebaseUtil.openFbReference("appointment", this);
        FirebaseUtil.userAppoints();

    }
}
