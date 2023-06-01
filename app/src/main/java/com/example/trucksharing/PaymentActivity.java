package com.example.trucksharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Properties config = new Properties();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("paypal_config.properties");
            config.load(inputStream);
            String clientId = config.getProperty("clientId");
            PayPalConfiguration configs = new PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
                    .clientId(clientId);

            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configs);
            startService(intent);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, PayPalService.class));
    }
}