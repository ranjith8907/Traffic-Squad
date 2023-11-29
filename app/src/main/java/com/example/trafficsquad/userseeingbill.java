package com.example.trafficsquad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class userseeingbill extends AppCompatActivity {
    private TextView Bills,totalamount,titlebill;
    private Button pay;
    private ImageView billphoto;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageReference;
    private FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userseeingbill);
        String veicleno=getIntent().getStringExtra("vehicleno");
        String date=getIntent().getStringExtra("date");
        Bills=findViewById(R.id.Billes);
        titlebill=findViewById(R.id.titlebill);
        totalamount=findViewById(R.id.totalamount);
        billphoto=findViewById(R.id.billphoto);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("VehicleFines").child(veicleno).child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Map<String, String> map = (Map) datasnapshot.getValue();
                String Fines = map.get("Fines").toString();
                String Total1 = map.get("Total").toString();
                String name=map.get("Username").toString();
                titlebill.setText("BILL("+date+")");
                Bills.setText("Name:"+name+" & "+"NO:"+veicleno+"\n"+"Fines & Rules \n0"+Fines);
                totalamount.setText("Total="+Total1);
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mStorageReference=FirebaseStorage.getInstance().getReference().child("Traffic_squad/").child(veicleno).child(date);
        try {
            final File LocateFile=File.createTempFile(date,"jpg");
            mStorageReference.getFile(LocateFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(userseeingbill.this,"image Retrived",Toast.LENGTH_LONG).show();
                            Bitmap bitmap= BitmapFactory.decodeFile(LocateFile.getAbsolutePath());
                            billphoto.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(userseeingbill.this,"error occured",Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}