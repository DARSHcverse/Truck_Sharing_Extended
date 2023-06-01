package com.example.trucksharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.Manifest;
import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {

    MyDatabaseHelper myDatabaseHelper;

    TextView sender,pick,receiver,drop,wieght,type,width,height,length,vechile;

    private static final int CALL_PERMISSION_REQUEST_CODE = 1001;
    String PhoneNumber="1234567890";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        myDatabaseHelper=new MyDatabaseHelper(this);

        sender=findViewById(R.id.fromsender);
        pick=findViewById(R.id.pickuptime);
        receiver=findViewById(R.id.receivername);
        drop=findViewById(R.id.droptime);
        wieght=findViewById(R.id.WEIGHT);
        type=findViewById(R.id.TYPE);
        width=findViewById(R.id.WIDTH);
        height=findViewById(R.id.HEIGHT);
        length=findViewById(R.id.LENGTH);
        vechile=findViewById(R.id.VECHILE);
        Button button=findViewById(R.id.button);



        Intent getintent=getIntent();
        String User=getintent.getStringExtra("username");
        String Rname=getintent.getStringExtra("Rname");
        String Pick=getintent.getStringExtra("pickup");
        String Drop=getintent.getStringExtra("droptime");
        String Weight=getintent.getStringExtra("weight");
        String Type=getintent.getStringExtra("type");
        String Width=getintent.getStringExtra("width");
        String Height=getintent.getStringExtra("heights");
        String Length=getintent.getStringExtra("length");
        String Vechile=getintent.getStringExtra("vechi");



        sender.setText("From deakin");
        pick.setText("Pick Up Time: "+Pick);
        receiver.setText("To "+Rname);
        drop.setText("Drop Off Time: "+Drop);
        wieght.setText("Weight: "+Weight+"KG");
        type.setText("Type: "+Type);
        width.setText("Width: "+Width+"M");
        height.setText("Height: "+Height+"M");
        length.setText("Length: "+Length+"M");
        vechile.setText("Vechile Type: "+Vechile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(PhoneNumber);
            }
        });



    }
    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Request the CALL_PHONE permission if it is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        } else {
            // Start the call
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start the call
                makePhoneCall(PhoneNumber);
            } else {
                // Permission denied, show a message or handle the situation accordingly
            }
        }
    }

}