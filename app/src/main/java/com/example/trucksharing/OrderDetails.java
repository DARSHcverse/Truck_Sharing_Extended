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
        String Loc=getintent.getStringExtra("loc");
        String Dloc=getintent.getStringExtra("Dloc");



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
                Intent intent=new Intent(OrderDetails.this,EsitimateMap.class);
                intent.putExtra("LOC",Loc);
                intent.putExtra("DOP",Dloc);
                startActivity(intent);
            }
        });



    }

}