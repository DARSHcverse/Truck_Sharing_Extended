package com.example.trucksharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.trucksharing.databinding.ActivityNewOrderBinding;
import com.example.trucksharing.databinding.ActivitySignupPageBinding;

public class NewOrder extends AppCompatActivity {

    ActivityNewOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        binding= ActivityNewOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(NewOrder.this,NewOrderTwo.class);
                intent.putExtra("name",binding.Rname.getText().toString());
                intent.putExtra("pickDate",binding.date.getText().toString());
                intent.putExtra("time",binding.time.getText().toString());
                intent.putExtra("loc",binding.Rlocation.getText().toString());
                startActivity(intent);


            }
        });
    }
}