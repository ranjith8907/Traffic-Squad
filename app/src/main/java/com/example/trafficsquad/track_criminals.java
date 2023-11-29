package com.example.trafficsquad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class track_criminals extends AppCompatActivity {
    private Button send,receive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_track_criminals);
        send=findViewById(R.id.send);
        receive=findViewById(R.id.recive);
        String officierid=getIntent().getStringExtra("message1");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(track_criminals.this,"send",Toast.LENGTH_LONG).show();
                Intent send_details=new Intent(track_criminals.this,send_criminals_details.class);
                startActivity(send_details);
            }
        });
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(track_criminals.this,"receive",Toast.LENGTH_LONG).show();
                Intent Receive_details=new Intent(track_criminals.this,Receive_criminals_details.class);
                Receive_details.putExtra("message1",officierid);
                startActivity(Receive_details);
            }
        });
    }
}