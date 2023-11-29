package com.example.trafficsquad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class officer_options extends AppCompatActivity {
    private ListView officer_option;
    private TextView off_id;
    private Button off_signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_officer_options);
        officer_option=findViewById(R.id.officer_option);
        String h=getIntent().getStringExtra("sucess");
        off_id=findViewById(R.id.off__id);
        off_signout=findViewById(R.id.offsignout);
        String officerid=getIntent().getStringExtra("message");
        String offoption[]=new String[]{
                "Make Challan","Ckeck RC","Check Insurance","Track Criminals"
        };
        ArrayAdapter<String> offioption=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,offoption);
        officer_option.setAdapter(offioption);
        off_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signout=new Intent(officer_options.this,officer_loginpage.class);
                startActivity(signout);
                finish();
            }
        });
        officer_option.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==0){
                    Intent addofficier1=new Intent(officer_options.this,Fain_page.class);
                    addofficier1.putExtra("message1",officerid);
                    startActivity(addofficier1);


                }
                if(position==3){
                    Intent trackcrimanals=new Intent(officer_options.this,track_criminals.class);
                    trackcrimanals.putExtra("message1",officerid);
                    startActivity(trackcrimanals);
                }
            }
        });

    }
}