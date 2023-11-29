package com.example.trafficsquad;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Properties;

import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;


public class challan extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private FirebaseStorage storage;
    private StorageReference storagereference;
    private ImageButton mCaptureBtn;
    private Button submit;
    private ImageView mImageView;
    private String date,vehicleno,Emailid,yougmail,password,totalrules,total;
    private String fin="Sucess";

    Uri image_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challan);

        storage=FirebaseStorage.getInstance();
        storagereference=storage.getReference();
        date=getIntent().getStringExtra("TodayDate");
        vehicleno=getIntent().getStringExtra("Vehicleno");
        totalrules=getIntent().getStringExtra("total rules");
        total=getIntent().getStringExtra("total");
        mImageView = (ImageView)findViewById(R.id.imageview);
        mCaptureBtn = (ImageButton)findViewById(R.id.camera1);
        Emailid=getIntent().getStringExtra("emailid");
        submit = (Button)findViewById(R.id.submit);
        yougmail="trafficsquadofficial@gmail.com";
        password="trafficsquadofficial@1";
        //button click
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if system os is >= marshmallow, request runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        openCamera();
                    }
                }
                else {
                    //system os < marshmallow
                    openCamera();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(image_uri != null){
                    uploadpicture(image_uri);
                    Properties properties=new Properties();
                    properties.put("mail.smtp.auth","true");
                    properties.put("mail.smtp.starttls.enable","true");
                    properties.put("mail.smtp.host","smtp.gmail.com");
                    properties.put("mail.smtp.port","587");

                    Session session= Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(yougmail,password);
                        }
                    });

                    try {
                        Message message=new MimeMessage(session);
                        message.setFrom(new InternetAddress(yougmail));
                        Address addressTo=new InternetAddress(Emailid);
                        message.setRecipient(Message.RecipientType.TO,addressTo);
                        message.setSubject("Your Offence");
                        message.setText("YOUTOFFENCES \n RULES & FINES:\n"+totalrules +"------------------------------------"+"\n"+"TOTAL="+total+
                                "\n\n\n"+"         IMAGE PROOF SEE TO TRAFIC SQUAD OFFICIAL APPLICATION....");
                        new SendMail().execute(message);
                    }
                    catch(MessagingException e){
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(challan.this,"please take image",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private class SendMail extends AsyncTask<Message,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Message... messages) {
            try{
                Transport.send(messages[0]);
                return "Success";
            }
            catch(MessagingException e){
                e.printStackTrace();
                return "Error";
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("Success")) {
                Toast.makeText(challan.this, "Mail sent", Toast.LENGTH_LONG).show();
            }
            else{
                    Toast.makeText(challan.this, "Something went wrong ?", Toast.LENGTH_LONG).show();
                }
            }
    }
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    //handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera();
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //called when image was captured from camera

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //set the image captured to our ImageView
            mImageView.setImageURI(image_uri);
        }
    }

    private void uploadpicture(Uri imageUri){
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("uploading image...");
        pd.show();;
        StorageReference river=storagereference.child("Traffic_squad/").child(vehicleno).child(date);
        river.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content),"image.upload.",Snackbar.LENGTH_LONG).show();
                Intent goint=new Intent(challan.this,officer_options.class);
                goint.putExtra("sucess",fin);
                startActivity(goint);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Faild to upload.",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progresspercent=(100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("percentage:"+(int)progresspercent+ "%");
            }
        });

    }
    




}


