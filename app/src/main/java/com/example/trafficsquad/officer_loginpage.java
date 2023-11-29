package com.example.trafficsquad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class officer_loginpage extends AppCompatActivity {
    private EditText offuser,offpass;
    private Button offcon;
    private FirebaseDatabase firebaseDatabase;
    private String ouser,opass,offpass1,offuser1;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_officer_loginpage);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        offpass=findViewById(R.id.officerpass);
        offuser=findViewById(R.id.officeuser);
        offcon=findViewById(R.id.continue2);
        progressDialog=new ProgressDialog(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        offcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouser=offuser.getText().toString().trim();
                opass=offpass.getText().toString().trim();
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                if(!TextUtils.isEmpty(ouser)) {
                    if (!TextUtils.isEmpty(opass)) {
                        firebaseDatabase.getReference("Officer").child(ouser).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                Map<String, String> map = (Map) datasnapshot.getValue();
                                offpass1 = map.get("Password").toString();
                                offuser1 = map.get("id").toString();
                                if (offuser1.equals(ouser)) {
                                    if (offpass1.equals(opass)) {
                                        Intent offlogin = new Intent(officer_loginpage.this, officer_options.class);
                                        offlogin.putExtra("message",offuser1);
                                        startActivity(offlogin);
                                        finish();
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        offpass.setError("Enter Correct Password");
                                        return;
                                    }
                                }
                                else {
                                    progressDialog.dismiss();
                                    offuser.setError("Enter Correct User ID");
                                    return;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                            progressDialog.dismiss();
                        offpass.setError("Enter the password");
                        return;
                    }
                }
                else{
                    progressDialog.dismiss();
                    offuser.setError("Enter the User ID");
                    return;
                }

            }
        });
    }
}