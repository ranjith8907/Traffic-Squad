package com.example.trafficsquad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Receive_criminals_details extends AppCompatActivity {
    private TextView C_NAME,C_GMAIL,C_VEHICLENO,C_VEHICLENAME,C_RTO,C_LOCATION;
    private FirebaseDatabase firebaseDatabase;
    String vehicleno1,c_name,c_gmail,c_vehicle,c_rto,station;
    String location1,location2,location3,location4,location5;
    private Button view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_receive_criminals_details);
        String officierid=getIntent().getStringExtra("message1");
        C_NAME=findViewById(R.id.C_NAME);
        C_GMAIL= findViewById(R.id.C_AGE);
        C_VEHICLENO=findViewById(R.id.C_VEHICLENO);
        C_VEHICLENAME=findViewById(R.id.C_VEHICLENAME);
        C_RTO=findViewById(R.id.C_RTOOFFICE);
        C_LOCATION=findViewById(R.id.C_LOCATION);
        view=findViewById(R.id.C_BACK);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("C_VEHICLENO").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Map<String, String> map = (Map) datasnapshot.getValue();
                vehicleno1=map.get("vechileno").toString();
                C_VEHICLENO.setText(vehicleno1);
                System.out.println(vehicleno1);
                firebaseDatabase=FirebaseDatabase.getInstance();
                firebaseDatabase.getReference("Vehicle").child(vehicleno1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        Map<String, String> map = (Map) datasnapshot.getValue();
                        c_name=map.get("Username").toString();
                        c_vehicle=map.get("Vehicle Name").toString();
                        c_gmail=map.get("Email Id").toString();
                        c_rto=map.get("RTO Office name").toString();

                        C_NAME.setText(c_name);
                        C_VEHICLENAME.setText(c_vehicle);
                        C_GMAIL.setText(c_gmail);
                        C_RTO.setText(c_rto);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("Officer").child(officierid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Map<String, String> map = (Map) datasnapshot.getValue();
                station=map.get("station").toString();
                firebaseDatabase=FirebaseDatabase.getInstance();
                firebaseDatabase.getReference("Track Criminals").child(station).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        Map<String, String> map = (Map) datasnapshot.getValue();
                        location1=map.get("1").toString();
                        location2=map.get("2").toString();
                        location3=map.get("3").toString();
                        location4=map.get("4").toString();
                        //location5=map.get("5").toString();

                        C_LOCATION.setText(location1+" "+location2+" "+location3+" "+location4);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(Receive_criminals_details.this,track_criminals.class);
                startActivity(back);
                finish();
            }
        });

    }
}