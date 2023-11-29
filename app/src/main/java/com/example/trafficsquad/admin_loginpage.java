package com.example.trafficsquad;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class admin_loginpage extends AppCompatActivity {
    private Button gotoadminop;
    private EditText adminpass,adminuser;
    private String AdminPassword,Adminuser;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private String adminuser1,adminpass1,adminuserid,Adminuserpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_loginpage);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gotoadminop=findViewById(R.id.gotoadoptions);
        adminuser=findViewById(R.id.adminid);
        adminpass=findViewById(R.id.adminpass);
        progressDialog=new ProgressDialog(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        AdminPassword="Ranjith8907";
        Adminuser="9842619939";
        gotoadminop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                adminuserid=adminuser.getText().toString().trim();
                Adminuserpass=adminpass.getText().toString().trim();
                if(!TextUtils.isEmpty(adminuserid)){
                    if(!TextUtils.isEmpty(Adminuserpass)){
                        firebaseDatabase.getReference("Admin").child(adminuserid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                Map<String, String> map1 = (Map) datasnapshot.getValue();
                                adminpass1= map1.get("Password").toString();
                                adminuser1= map1.get("id").toString();
                                if (adminuser1.equals(adminuserid)) {
                                    if (adminpass1.equals(Adminuserpass)) {
                                        Intent offlogin = new Intent(admin_loginpage.this,Admin_options.class);
                                        startActivity(offlogin);
                                        finish();
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        adminpass.setError("Enter Correct Password");
                                        return;
                                    }

                                }
                                else {
                                    progressDialog.dismiss();
                                    adminuser.setError("Enter Correct User ID");
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
                        adminpass.setError("Enter the password");
                        return;

                }
            }
                else{
                    progressDialog.dismiss();
                    adminuser.setError("Enter the User ID");
                    return;
            }

        }
    });
        adminuser.getText().clear();
        adminpass.getText().clear();
    }
}