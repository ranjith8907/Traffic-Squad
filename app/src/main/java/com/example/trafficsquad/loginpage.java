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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class loginpage extends AppCompatActivity {
    private FirebaseAuth peoplefirebaseAuth;
    private EditText user,pass,vehicleno;
    private TextView signup;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
    private Button conti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loginpage);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        peoplefirebaseAuth=FirebaseAuth.getInstance();
        vehicleno=findViewById(R.id.VehicleNo);
        user=findViewById(R.id.peopleuser);
        pass=findViewById(R.id.peoplepass);
        conti=findViewById(R.id.Continue);
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);
        signup=findViewById(R.id.signup);
        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=user.getText().toString().trim();
                String password1=pass.getText().toString().trim();
                String veicleno=vehicleno.getText().toString().trim();
                if(TextUtils.isEmpty(userID) && TextUtils.isEmpty(password1) && TextUtils.isEmpty(veicleno)){
                    user.setError("please enter the Email or Pass Vehicleno");
                    return;
                }
                else{
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    peoplefirebaseAuth.signInWithEmailAndPassword(userID,password1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(loginpage.this,"Please verify the your email and continue",Toast.LENGTH_SHORT).show();
                                        peoplefirebaseAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            if(peoplefirebaseAuth.getCurrentUser().isEmailVerified()){
                                                                firebaseDatabase.getReference("Vehicle").child(veicleno).addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                                                        Map<String, String> map = (Map) datasnapshot.getValue();
                                                                        String Emailid = map.get("Email Id").toString();
                                                                        if (userID.equals(Emailid)) {
                                                                            Intent peoconti = new Intent(loginpage.this, people_options.class);
                                                                            peoconti.putExtra("email", userID);
                                                                            peoconti.putExtra("vehicleno", veicleno);
                                                                            startActivity(peoconti);
                                                                            finish();
                                                                        }
                                                                        else{
                                                                            progressDialog.dismiss();
                                                                            Toast.makeText(loginpage.this,"login failed",Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                    }
                                                                });

                                                            }
                                                            else{
                                                                progressDialog.dismiss();
                                                                Toast.makeText(loginpage.this,"please try again",Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                        else{
                                                            progressDialog.dismiss();
                                                        }
                                                    }
                                                });

                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(loginpage.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
                            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent peoconti=new Intent(loginpage.this,peoplesignip.class);
                startActivity(peoconti);


            }
        });
    }
}