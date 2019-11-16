package com.example.barberappoint;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class BookAppointment extends AppCompatActivity {

    private TabHost tabs;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    Toolbar mToolbar;
    static String selectedDateStr;
    static String selectedTimeStr;
    static String selectedBarberStr;
    RadioButton rbTime;
    TextView service;
    TextView barber;
    TextView date;
    TextView time;
    String strService;
    Appointment appoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_appointment);

        FirebaseUtil.openFbReference("appointment", this);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        appoint = new Appointment();

        service = findViewById(R.id.service);
        barber = findViewById(R.id.barber);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Realiza tu reserva");

        tabs = (TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Barbero");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Cuando");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Confirmar");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        final HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                selectedDateStr = DateFormat.format("EEE, MMM d, yyyy", date).toString();
            }
        });

        RadioGroup rgBarbers = findViewById(R.id.rgBarbers);
        rgBarbers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbTime = findViewById(checkedId);
                selectedBarberStr = rbTime.getText().toString();
            }
        });

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbTime = findViewById(checkedId);
                selectedTimeStr = rbTime.getText().toString();
            }
        });

        Intent intent = getIntent();
        strService = intent.getStringExtra("service");
    }

    public void onButtonDateTimeClicked(View view){
        if(view.getId() == R.id.nextBarber){
            tabs.setCurrentTab(1);
        }
    }

    public void onButtonContinueClicked(View view){
        if(view.getId() == R.id.nextDateTime){
            tabs.setCurrentTab(2);
            service.setText(strService);
            barber.setText(selectedBarberStr);
            date.setText(selectedDateStr);
            time.setText(selectedTimeStr);
        }
    }

    public void onButtonConfirmClicked(View view){
        if(view.getId() == R.id.btnConfirm) {
            saveAppointment();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void saveAppointment(){
        //appoint.setName(txtName.getText().toString());
        appoint.setService(strService);
        appoint.setDate(selectedDateStr);
        appoint.setHour(selectedTimeStr);
        //appoint.setPrice(txtPrice.getText().toString());
        appoint.setBarber(selectedBarberStr);
        if(appoint.getId() == null){
            mDatabaseReference.push().setValue(appoint);
        }
        else{
            mDatabaseReference.child(appoint.getId()).setValue(appoint);
        }
    }
  }



