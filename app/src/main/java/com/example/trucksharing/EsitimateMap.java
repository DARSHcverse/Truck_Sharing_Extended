package com.example.trucksharing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class EsitimateMap extends AppCompatActivity implements OnMapReadyCallback {

    MyDatabaseHelper myDatabaseHelper;
    TextView Pickuploc, Dropoffloc;
    TextView AppFare, AppTime;
    private static final int CALL_PERMISSION_REQUEST_CODE = 1001;
    private static final int REQUEST_CODE_PAYPAL_PAYMENT = 1234;
    Button BookNow, Call;

    String PhoneNumber = "0377778888";

    SupportMapFragment mapFragment;

    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esitimate_map);

        myDatabaseHelper = new MyDatabaseHelper(this);

        Intent getintent = getIntent();
        String location = getintent.getStringExtra("LOC");
        String DropLoc = getintent.getStringExtra("DOP");


        Pickuploc = findViewById(R.id.PickUPLoc);
        Dropoffloc = findViewById(R.id.DropOFFLoc);
        AppFare = findViewById(R.id.ApproFare);
        AppTime = findViewById(R.id.TravelTime);

        Call = findViewById(R.id.CallD);

        BookNow = findViewById(R.id.Book);

        Pickuploc.setText("Pickup Location: " + location);
        Dropoffloc.setText("Drop-Off Location: " + DropLoc);

        Random random = new Random();
        int TravelTime = random.nextInt(50 - 15 + 1) + 15;

        int Fare = random.nextInt(80 - 10 + 2) + 15;

        AppFare.setText("Approx. Fare: $" + Fare);
        AppTime.setText("Approx. Travel Time: " + TravelTime + " min");

        BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PayPalPayment payment = new PayPalPayment(new BigDecimal(Fare), "AUD", "Payment for Goods", PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(EsitimateMap.this, PaymentActivity.class);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                startActivityForResult(intent, REQUEST_CODE_PAYPAL_PAYMENT);

            }
        });

        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(PhoneNumber);

            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        } else {
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                makePhoneCall(PhoneNumber);
            } else {
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PAYPAL_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    String paymentId = confirmation.getProofOfPayment().getPaymentId();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

            }
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(this);
        myDatabaseHelper = new MyDatabaseHelper(this);

        try {
            Pickuploc = findViewById(R.id.PickUPLoc);
            Dropoffloc = findViewById(R.id.DropOFFLoc);
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setTrafficEnabled(true);

            List<Address> addresses = geocoder.getFromLocationName(Pickuploc.getText().toString(), 1);
            List<Address> addresses2 = geocoder.getFromLocationName(Dropoffloc.getText().toString(), 1);

            Address address = addresses.get(0);
            Address address2 = addresses2.get(0);

            double latitude = address.getLatitude();
            double longitude = address.getLongitude();

            double latitude2 = address2.getLatitude();
            double longitude2 = address2.getLongitude();

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.setTrafficEnabled(true);

            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(new LatLng(latitude, longitude))
                    .add(new LatLng(latitude2, longitude2))
                    .width(5)
                    .color(Color.RED);

            googleMap.addPolyline(polylineOptions);

            LatLng loc = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions()
                    .position(loc)
                    .title("Pickup in " +Pickuploc.getText().toString()));

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            LatLng loc2 = new LatLng(latitude2, longitude2);
            googleMap.addMarker(new MarkerOptions()
                    .position(loc2)
                    .title("Drop in " +Dropoffloc.getText().toString()));

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}