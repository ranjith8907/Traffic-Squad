package com.example.trafficsquad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class people_options extends AppCompatActivity {
    private ListView peopleoption;
    private TextView textView;
    private Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_people_options);
        textView=findViewById(R.id.emailview);
        signout=findViewById(R.id.peoplesignout);
        peopleoption=findViewById(R.id.peopleoption);
        String email=getIntent().getStringExtra("email");
        String vehicleno=getIntent().getStringExtra("vehicleno");
        textView.setText(vehicleno);
        String pe_options[]=new String[]{
                "Check Insurance","Profile","Check Challan","Pay Challan"
        };
        ArrayAdapter<String> peo_adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,pe_options);
        peopleoption.setAdapter(peo_adapter);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signoutpeo=new Intent(people_options.this,loginpage.class);
                startActivity(signoutpeo);
                finish();
            }
        });
        peopleoption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==2){
                    Intent inte=new Intent(people_options.this,checkchallan.class);
                    inte.putExtra("vehicleno",vehicleno);
                    startActivity(inte);
                }
                if(position==1){
                    Intent inte=new Intent(people_options.this,profilepage.class);
                    inte.putExtra("vehicleno",vehicleno);
                    startActivity(inte);
                }

            }
        });
    }
}