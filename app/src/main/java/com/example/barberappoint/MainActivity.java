package com.example.barberappoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TabHost;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ViewPager viewPager;
    SpringDotsIndicator springDotsIndicator;
    private TabHost tabs;
    private TabHost.TabSpec spec;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    CheckBox corte;
    CheckBox manicura;
    CheckBox tintado;
    CheckBox barba;
    CheckBox cerquillo;
    Button btnReserva;
    private static int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView)findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(this);

        Resources res = getResources();

        tabs = (TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Servicios");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Informacion");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        springDotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        springDotsIndicator.setViewPager(viewPager);

        corte = findViewById(R.id.check_fullCut);
        manicura = findViewById(R.id.check_manicure);
        tintado = findViewById(R.id.check_tinted);
        barba = findViewById(R.id.check_beard);
        cerquillo = findViewById(R.id.check_bangs);
        btnReserva = findViewById(R.id.btnReserva);

        btnReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String service = "";

                if(corte.isChecked()){
                    service ="- " + corte.getText().toString();
                }
                if(manicura.isChecked()){
                    service = service + "- " + manicura.getText().toString();
                }
                if(tintado.isChecked()){
                    service = service + "- " + tintado.getText().toString();
                }
                if(barba.isChecked()){
                    service = service + "- " + barba.getText().toString();
                }
                if(cerquillo.isChecked()){
                    service = service + "- " + cerquillo.getText().toString();
                }
                Intent intent = new Intent(view.getContext(), BookAppointment.class);
                intent.putExtra("service", service);
                startActivity(intent);
            }
        });
    }

    void checkChecked(boolean checked){
        if (checked){
            count++;
            if(count > 1)
                btnReserva.setVisibility(View.VISIBLE);
        }
        else{
            count--;
            if(count == 1)
                btnReserva.setVisibility(View.INVISIBLE);
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox)view).isChecked();

        switch(view.getId()) {
            case R.id.check_fullCut:
                checkChecked(checked);
                break;
            case R.id.check_manicure:
                checkChecked(checked);
                break;
            case R.id.check_beard:
                checkChecked(checked);
                break;
            case R.id.check_bangs:
                checkChecked(checked);
                break;
            case R.id.check_tinted:
                checkChecked(checked);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFbReference("appointment",this);
        FirebaseUtil.attachListener();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_account:
                return true;
            case R.id.nav_bookings:
                Intent intent = new Intent(this, UserAppointments.class);
                startActivity(intent);
                return true;
            case R.id.nav_logout:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseUtil.attachListener();
                            }
                        });
                finish();
                FirebaseUtil.detachListener();
                return true;
        }

        return false;
    }


}

