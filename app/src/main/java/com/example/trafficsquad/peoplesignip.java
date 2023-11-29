package com.example.trafficsquad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

import java.util.regex.Pattern;

public class peoplesignip extends AppCompatActivity {
    private EditText peopname,peoemail,peopass,peoconpass;
    private TextView peologin;
    private Button peoregister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String mail,name,pass,passcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_peoplesignip);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        firebaseAuth=firebaseAuth.getInstance();
        peopname=findViewById(R.id.PeopleName);
        peoemail=findViewById(R.id.peopleEmail);
        peopass=findViewById(R.id.Peoplepass1);
        peoconpass=findViewById(R.id.peopConfirmPass);
        peoregister=findViewById(R.id.peoplesign);
        peologin=findViewById(R.id.peoplelsignogin);
        progressDialog=new ProgressDialog(this);
        peologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent peologin=new Intent(peoplesignip.this,loginpage.class);
                startActivity(peologin);
                finish();
            }
        });
        peoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail=peoemail.getText().toString().trim();
                name=peopname.getText().toString().trim();
                pass=peopass.getText().toString().trim();
                passcon=peoconpass.getText().toString().trim();

                if(mail.isEmpty()){
                    peoemail.setError("Enter your email");
                    return;
                }
                else if(name.isEmpty()){
                    peopname.setError("Enter your name");
                    return;
                }
                else if(pass.isEmpty()){
                    peopass.setError("Enter your Password");
                    return;
                }
                else if(passcon.isEmpty()){
                    peoconpass.setError("Enter your Password");
                    return;
                }
                else if(!pass.equals(passcon)){
                    peoconpass.setError("Different Password");
                    return;
                }
                else if(passcon.length()<6){
                    peopass.setError("Password length should >6");
                    return;
                }
                else if(!isValliEmail(mail)){
                    peoemail.setError("Invaild Email");
                }
                progressDialog.setMessage("Please wail...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                firebaseAuth.createUserWithEmailAndPassword(mail,passcon)
                        .addOnCompleteListener(peoplesignip.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(peoplesignip.this,"sucessfully created account",Toast.LENGTH_SHORT).show();
                                    Intent gotologin=new Intent(peoplesignip.this,loginpage.class);
                                    startActivity(gotologin);
                                    finish();
                                }
                                else{
                                    Toast.makeText(peoplesignip.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                                peoemail.getText().clear();
                                peopname.getText().clear();
                                peopass.getText().clear();
                                peoconpass.getText().clear();
                            }
                        });
            }
        });
    }
    private Boolean isValliEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}