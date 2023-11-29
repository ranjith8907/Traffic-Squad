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
import android.widget.Toast;

public class Admin_options extends AppCompatActivity {
    private ListView adminoption;
    private TextView Adminuserid;
    private Button adminsignout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        adminoption=findViewById(R.id.admin_option);
        Adminuserid=findViewById(R.id.adminIDtext);
        adminsignout=findViewById(R.id.adminsignout);
        String adminop[]=new String[]{
               "Add Officer","Check Officer","Status OR Challan","Vechicle Details Add"
        };
        ArrayAdapter<String> adoption=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,adminop);
        adminoption.setAdapter(adoption);
        adminsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signoutadmin=new Intent(Admin_options.this,admin_loginpage.class);
                startActivity(signoutadmin);
                finish();
            }
        });
        adminoption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==0){
                    Intent addofficier=new Intent(Admin_options.this,add_officier.class);
                    startActivity(addofficier);
                }
                else if(position==3){
                    Intent addofficier1=new Intent(Admin_options.this,add_vehicle.class);
                    startActivity(addofficier1);
                }
                else{
                    System.out.println("hi");
                }
            }
        });
    }
}