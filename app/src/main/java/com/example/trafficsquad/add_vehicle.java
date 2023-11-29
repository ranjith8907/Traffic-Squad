package com.example.trafficsquad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class add_vehicle extends AppCompatActivity {
    private EditText username,emailid,vehiclename,vehicleno,rtoname,resgitration,valucation;
    private Button vehicle_add;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        username=findViewById(R.id.vehicleusername);
        emailid=findViewById(R.id.useremailid);
        vehiclename=findViewById(R.id.vehiclename);
        vehicleno=findViewById(R.id.vehicleno);
        rtoname=findViewById(R.id.rtoname);
        resgitration=findViewById(R.id.resgitration);
        valucation=findViewById(R.id.valucation);
        vehicle_add=findViewById(R.id.vehicle_add);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Vehicle");
        vehicle_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernamev=username.getText().toString().trim();
                String emailidv=emailid.getText().toString().trim();
                String vehiclenamev=vehiclename.getText().toString().trim();
                String vehiclenov=vehicleno.getText().toString().trim();
                String rtonamev=rtoname.getText().toString().trim();
                String resgitrationv=resgitration.getText().toString().trim();
                String valucationv=valucation.getText().toString().trim();

                Map<String,String> map=new HashMap<>();
                map.put("Username",usernamev);
                map.put("Email Id",emailidv);
                map.put("Vehicle Name",vehiclenamev);
                map.put("VehicleNo",vehiclenov);
                map.put("RTO Office name",rtonamev);
                map.put("Resgitration",resgitrationv);
                map.put("Vehicle Valuation",valucationv);

                reference.child(vehiclenov).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(add_vehicle.this,"Sucessfully Vehicle Added",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(add_vehicle.this,"UnSucessfully Vehicle Added",Toast.LENGTH_SHORT).show();
                        }
                        username.getText().clear();
                        emailid.getText().clear();
                        vehiclename.getText().clear();
                        vehicleno.getText().clear();
                        rtoname.getText().clear();
                        resgitration.getText().clear();
                        valucation.getText().clear();
                    }
                });
            }
        });

    }
}