package com.example.barberappoint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtName;
    EditText txtService;
    EditText txtDate;
    EditText txtHour;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        FirebaseUtil.openFbReference("appointment");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        txtName = findViewById(R.id.txtName);
        txtService = findViewById(R.id.txtService);
        txtDate = findViewById(R.id.txtDate);
        txtHour = findViewById(R.id.txtHour);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                saveAppointment();
                Toast.makeText(this, "Cita guardada", Toast.LENGTH_LONG).show();
                clean();
                break;
        }
    }

    private void saveAppointment(){
        String name = txtName.getText().toString();
        String service = txtService.getText().toString();
        String date = txtDate.getText().toString();
        String hour = txtHour.getText().toString();
        Appointment appoint = new Appointment(name, service, date, hour);
        mDatabaseReference.push().setValue(appoint);
    }
    private void clean() {
        txtName.setText("");
        txtService.setText("");
        txtDate.setText("");
        txtHour.setText("");
    }
}
