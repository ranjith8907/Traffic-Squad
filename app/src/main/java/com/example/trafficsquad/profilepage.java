package com.example.trafficsquad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class profilepage extends AppCompatActivity {
    private Button Back;
    private FirebaseDatabase firebaseDatabase;
    private TextView name1,vehiclename,vehicleno,rto1,email1,resdate,valuation;
    private String name,vehiclename1,vehicleno1,email,Rto,resdata,end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        Back=findViewById(R.id.back);
        name1=findViewById(R.id.username1);
        vehiclename=findViewById(R.id.vehiclename1);
        vehicleno=findViewById(R.id.vehicleno1);
        rto1=findViewById(R.id.rto1);
        email1=findViewById(R.id.EMAILID1);
        resdate=findViewById(R.id.resdate1);
        valuation=findViewById(R.id.VALUATION1);
        String vehicle=getIntent().getStringExtra("vehicleno").toString();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back1=new Intent(profilepage.this,people_options.class);
                startActivity(back1);
                finish();
            }
        });
            firebaseDatabase=FirebaseDatabase.getInstance();
            firebaseDatabase.getReference("Vehicle").child(vehicle).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    Map<String, String> map = (Map) datasnapshot.getValue();
                    name = map.get("Username").toString();
                    vehiclename1 = map.get("Vehicle Name").toString();
                    vehicleno1=map.get("VehicleNo").toString();
                    email=map.get("Email Id").toString();
                    Rto=map.get("RTO Office name").toString();
                    resdata=map.get("Resgitration").toString();
                    end=map.get("Vehicle Valuation").toString();

                    name1.setText(name);
                    vehiclename.setText(vehiclename1);
                    vehicleno.setText(vehicleno1);
                    rto1.setText(Rto);
                    email1.setText(email);
                    resdate.setText(resdata);
                    valuation.setText(end);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }
}