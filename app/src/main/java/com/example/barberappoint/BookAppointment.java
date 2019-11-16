package com.example.barberappoint;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class BookAppointment extends AppCompatActivity {

    private TabHost tabs;
    private ImageButton btn1, btn2;
    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
     int mes = c.get(Calendar.MONTH);
     int dia = c.get(Calendar.DAY_OF_MONTH);
     int anio = c.get(Calendar.YEAR);
    EditText etFecha;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_appointment);

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
    }

    public void onButtonDateTimeClicked(View view){
        if(view.getId() == R.id.nextBarber){
            tabs.setCurrentTab(1);
        }

    }
  }


