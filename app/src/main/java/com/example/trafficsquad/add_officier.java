package com.example.trafficsquad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class add_officier extends AppCompatActivity {
    private EditText offemail,offname,offlevel,offphone,offid,offstation,offdept,offage;
    private Button offsumit;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private static int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_officier);
        offemail=findViewById(R.id.Officier_Email);
        offname=findViewById(R.id.officer_name);
        offage=findViewById(R.id.offcier_age);
        offlevel=findViewById(R.id.offcierlevel);
        offphone=findViewById(R.id.Officier_ph);
        offid=findViewById(R.id.offid);
        offstation=findViewById(R.id.Offstation);
        offdept=findViewById(R.id.OfficerDept);
        offsumit=findViewById(R.id.offcier_submit);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Officer");

        offsumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String count1=Integer.toString(count);
                String omail=offemail.getText().toString().trim();
                String oname=offname.getText().toString().trim();
                String oage=offage.getText().toString().trim();
                String olevel=offlevel.getText().toString().trim();
                String ophone=offphone.getText().toString().trim();
                String oid=offid.getText().toString().trim();
                String ostation=offstation.getText().toString().trim();
                String odept=offdept.getText().toString().trim();
                String oPassword=ophone+oname;
                Map<String,String> map=new HashMap<>();
                map.put("mail",omail);
                map.put("age",oage);
                map.put("phone",ophone);
                map.put("level",olevel);
                map.put("id",oid);
                map.put("station",ostation);
                map.put("dept",odept);
                map.put("Password",oPassword);
                map.put("name",oname);
                map.put("count",count1);
                reference.child(oid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(add_officier.this,"Sucessfully Officer Added",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(add_officier.this,"UnSucessfully Officer Added",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}