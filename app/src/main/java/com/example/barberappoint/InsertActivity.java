package com.example.barberappoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    EditText txtPrice;
    EditText txtBarber;
    Button btnSave;
    Button btnDelete;
    Appointment appoint;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        FirebaseUtil.openFbReference("appointment", this);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        txtName = findViewById(R.id.txtName);
        txtService = findViewById(R.id.txtService);
        txtDate = findViewById(R.id.txtDate);
        txtHour = findViewById(R.id.txtHour);
        txtPrice = findViewById(R.id.txtPrice);
        txtBarber = findViewById(R.id.txtBarber);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        Intent intent = getIntent();
        Appointment appoint = (Appointment) intent.getSerializableExtra("Appoint");
        if (appoint == null){
            appoint = new Appointment();
        }
        this.appoint = appoint;

        txtName.setText(appoint.getName());
        txtService.setText(appoint.getService());
        txtDate.setText(appoint.getDate());
        txtHour.setText(appoint.getHour());
        txtPrice.setText(appoint.getPrice());
        txtBarber.setText(appoint.getBarber());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                saveAppointment();
                Toast.makeText(this, "Cita guardada", Toast.LENGTH_LONG).show();
                clean();
                backToList();
                break;
            case R.id.btnDelete:
                deleteAppointment();
                Toast.makeText(this,"Cita borrada", Toast.LENGTH_LONG).show();
                backToList();
                break;
        }
    }

    private void saveAppointment(){
        appoint.setName(txtName.getText().toString());
        appoint.setService(txtService.getText().toString());
        appoint.setDate(txtDate.getText().toString());
        appoint.setHour(txtHour.getText().toString());
        appoint.setPrice(txtPrice.getText().toString());
        appoint.setBarber(txtBarber.getText().toString());
        if(appoint.getId() == null){
            mDatabaseReference.push().setValue(appoint);
        }
        else{
            mDatabaseReference.child(appoint.getId()).setValue(appoint);
        }
    }

    private void deleteAppointment(){
        if(appoint == null){
            Toast.makeText(this,"La cita no esta guardada y no se puede borrar.", Toast.LENGTH_SHORT).show();
            return;
        }
        mDatabaseReference.child(appoint.getId()).removeValue();
    }

    private void backToList(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void clean() {
        txtName.setText("");
        txtService.setText("");
        txtDate.setText("");
        txtHour.setText("");
        txtPrice.setText("");
        txtBarber.setText("");
    }
}
