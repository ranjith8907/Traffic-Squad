package com.example.trafficsquad;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class checkchallan extends AppCompatActivity {
    private EditText selectdate;
    private Button finalnext;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkchallan);
        final Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        finalnext=findViewById(R.id.finalnext);
        String vehicleno=getIntent().getStringExtra("vehicleno");
        selectdate=findViewById(R.id.finesdate);
        selectdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(checkchallan.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayofmonth) {
                month=month+1;
                String date=dayofmonth+"-"+month+"-"+year;
                selectdate.setText(date);
            }
        };
        finalnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finaedate=selectdate.getText().toString().trim();
                Intent intentsee=new Intent(checkchallan.this,userseeingbill.class);
                intentsee.putExtra("date",finaedate);
                intentsee.putExtra("vehicleno",vehicleno);
                startActivity(intentsee);
            }
        });

    }
}