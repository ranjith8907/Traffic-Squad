package com.example.trafficsquad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Fain_page extends AppCompatActivity {
    private EditText vehiclenofine,emailidfine,usernamefine,rtoofficename,todaydate;
    private CheckBox overspeeding,drunkdriving,helmet,signaljumping,minor,licence;
    private Button CHALLAN;
    private static int finecount=0;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private int overspeedinga=0,drunkdrivinga=0,helmata=0,singaljumpinga=0,minora=0,licnecea=0,total;
    private String officername,officerstation;
    private String totalrules,usernamef,emailidf,Rtoofficenamef,vehicleno1,vehiclenamef,date,overspeedingf,drunkdrivingsf,helmetf,signaljumpingf,minorf,licencef,total1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fain_page);
        String officerid=getIntent().getStringExtra("message1");
        CHALLAN=findViewById(R.id.CHALLAN);
        vehiclenofine=findViewById(R.id.vehiclenofine);
        emailidfine=findViewById(R.id.emailidfine);
        usernamefine=findViewById(R.id.usernamefine);
        rtoofficename=findViewById(R.id.rtoofficename);
        todaydate=findViewById(R.id.todaydate);
        overspeeding=findViewById(R.id.overspeeding);
        drunkdriving=findViewById(R.id.drunkdriving);
        helmet=findViewById(R.id.helmet);
        signaljumping=findViewById(R.id.signaljumping);
        minor=findViewById(R.id.minor);
        licence=findViewById(R.id.licence);
        database=FirebaseDatabase.getInstance();

        database.getReference("Officer").child(officerid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Map<String, String> map = (Map) datasnapshot.getValue();
                officername = map.get("name").toString().trim();
                officerstation = map.get("station").toString().trim();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        emailidfine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleno1=vehiclenofine.getText().toString().trim();
                database.getReference("Vehicle").child(vehicleno1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        Map<String, String> map = (Map) datasnapshot.getValue();
                        usernamef = map.get("Username").toString().trim();
                        emailidf = map.get("Email Id").toString().trim();
                        Rtoofficenamef=map.get("RTO Office name").toString().trim();
                        vehiclenamef=map.get("Vehicle Name").toString().trim();
                        emailidfine.setText(emailidf);
                        usernamefine.setText(usernamef);
                        rtoofficename.setText(Rtoofficenamef);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        CHALLAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernamef=usernamefine.getText().toString().trim();
                emailidf=emailidfine.getText().toString().trim();
                Rtoofficenamef=rtoofficename.getText().toString().trim();
                date=todaydate.getText().toString().trim();

                if(overspeeding.isChecked()){
                    overspeedingf=overspeeding.getText().toString().trim();
                    overspeedinga=500;
                }
                if(drunkdriving.isChecked()){
                    drunkdrivinga=1000;
                    drunkdrivingsf=drunkdriving.getText().toString().trim();
                }
                if(helmet.isChecked()){
                    helmata=200;
                    helmetf=helmet.getText().toString().trim();
                }
                if(signaljumping.isChecked()){
                    singaljumpinga=700;
                    signaljumpingf=signaljumping.getText().toString().trim();
                }
                if(minor.isChecked()){
                    minora=1500;
                    minorf=minor.getText().toString().trim();
                }
                if(licence.isChecked()){
                    licnecea=2000;
                    licencef=licence.getText().toString().trim();
                }
                else{
                    Toast.makeText(Fain_page.this,"Fill fines",Toast.LENGTH_SHORT).show();
                }
                totalrules="OVERSPEEDING"+"= "+overspeedinga+", \n"+"DRUNK DRIVING"+"= "+drunkdrivinga+" ,\n"+"WITHOUT HELMAT"+"= "+helmata+" ,\n"+"SIGNAL JUMPING"+"= "+singaljumpinga+" ,\n"+"DRIVING FOR MINOR"+"= "+minora+" ,\n"+"WITHOUT LICENSE"+"= "+licnecea+".\n";
                total=overspeedinga + drunkdrivinga + helmata +singaljumpinga + minora + licnecea;
                total1=Integer.toString(total);

                Map<String,String> map=new HashMap<>();
                map.put("Username",usernamef);
                map.put("Email Id",emailidf);
                map.put("Vehicle Name",vehiclenamef);
                map.put("VehicleNo",vehicleno1);
                map.put("RTO Office name",Rtoofficenamef);
                map.put("Date",date);
                map.put("Fines",totalrules);
                map.put("Total",total1);
                map.put("Officier Station",officerstation);
                map.put("Officier Name",officername);

                database=FirebaseDatabase.getInstance();
                reference=database.getReference("VehicleFines");
                reference.child(vehicleno1).child(date).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            finecount++;
                            Toast.makeText(Fain_page.this,"Next",Toast.LENGTH_SHORT).show();
                            Intent signout=new Intent(Fain_page.this,challan.class);
                            signout.putExtra("TodayDate",date);
                            signout.putExtra("Vehicleno",vehicleno1);
                            signout.putExtra("emailid",emailidf);
                            signout.putExtra("total rules",totalrules);
                            signout.putExtra("total",total1);
                            startActivity(signout);
                            finish();
                        }
                        else{
                            Toast.makeText(Fain_page.this,"NotValid Valid",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                database=FirebaseDatabase.getInstance();
                reference=database.getReference("VehicleFines");
                reference.child(vehicleno1).child(date).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Fain_page.this,"Next",Toast.LENGTH_SHORT).show();
                            Intent signout=new Intent(Fain_page.this,challan.class);
                            signout.putExtra("TodayDate",date);
                            signout.putExtra("Vehicleno",vehicleno1);
                            signout.putExtra("emailid",emailidf);
                            signout.putExtra("total rules",totalrules);
                            signout.putExtra("total",total1);
                            startActivity(signout);
                            finish();
                        }
                        else{
                            Toast.makeText(Fain_page.this,"NotValid Valid",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}