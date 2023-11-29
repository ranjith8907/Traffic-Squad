package com.example.trafficsquad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class send_criminals_details extends AppCompatActivity {
    private EditText criminalname,criminalvehicleno,criminallocation;
    private Button sendtoofficer;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_send_criminals_details);
        criminallocation=findViewById(R.id.criminal_location);
        criminalname=findViewById(R.id.criminal_name);
        criminalvehicleno=findViewById(R.id.criminal_vehicle_no);
        sendtoofficer=findViewById(R.id.send_to_officer);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("C_VEHICLENO");
        sendtoofficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location=criminallocation.getText().toString().trim();
                String name=criminalname.getText().toString().trim();
                String vehicleno=criminalvehicleno.getText().toString().trim();
                if(TextUtils.isEmpty(vehicleno)){
                    criminalvehicleno.setError("Please enter the vehicleno");
                }
                if(TextUtils.isEmpty(name)){
                    criminalname.setError("Please enter the name");
                }
                if(TextUtils.isEmpty(location)) {
                    criminallocation.setError("Please enter the location");
                }
                else {
                    MST obj = new MST();
                    String destination[] = {"THIRUPURAKUNDROM", "ARAPALAYAM", "PERIYAR", "MATTUTHAVANI", "PUDUR"};
                    int graph[][] = new int[][]{{0, 10, 14, 20, 0},
                            {10, 0, 0, 16, 0},
                            {14, 0, 0, 10, 0},
                            {20, 16, 10, 0, 13},
                            {0, 0, 0, 13, 0}};
                    obj.primMST(graph, destination,location,vehicleno,name);
                    Map<String, String> map = new HashMap<>();
                    map.put("vechileno",vehicleno);
                    reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                  Toast.makeText(send_criminals_details.this,"sucessfully uploaded",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(send_criminals_details.this,"unsucessfully uploaded",Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
            }
        });

    }
}