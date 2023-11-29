package com.example.trafficsquad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class menuoption extends AppCompatActivity {

    private Button people,officer,admin;
    private TextView or,or1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menuoption);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        people=findViewById(R.id.peopleoption);
        officer=findViewById(R.id.offers);
        admin=findViewById(R.id.admin);
        or=findViewById(R.id.or);
        or1=findViewById(R.id.or1);
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(menuoption.this,admin_loginpage.class);
                startActivity(intent1);
            }
        });
        officer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(menuoption.this,officer_loginpage.class);
                startActivity(intent2);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(menuoption.this,loginpage.class);
                startActivity(intent2);
            }
        });

    }
}