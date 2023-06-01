package com.example.trucksharing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardRequirements;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Properties;
import java.util.zip.Inflater;

public class EsitimateMap extends AppCompatActivity {

    MyDatabaseHelper myDatabaseHelper;
    TextView Pickuploc, Dropoffloc,AppFare,AppTime;
    private static final int CALL_PERMISSION_REQUEST_CODE = 1001;
    private static final int REQUEST_CODE_PAYPAL_PAYMENT = 1234;
    Button BookNow,Call;

    String PhoneNumber="1234567890";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esitimate_map);

        myDatabaseHelper=new MyDatabaseHelper(this);

        Intent getintent=getIntent();
        String location=getintent.getStringExtra("LOC");
        String DropLoc=getintent.getStringExtra("DOP");

        System.out.println(location);

        Pickuploc=findViewById(R.id.PickUPLoc);
        Dropoffloc=findViewById(R.id.DropOFFLoc);
        AppFare=findViewById(R.id.ApproFare);
        AppTime=findViewById(R.id.TravelTime);

        Call=findViewById(R.id.CallD);

        BookNow=findViewById(R.id.Book);

        Double fare=50.00;


        BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PayPalPayment payment = new PayPalPayment(new BigDecimal("10.00"), "USD", "Payment description", PayPalPayment.PAYMENT_INTENT_SALE);

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



}