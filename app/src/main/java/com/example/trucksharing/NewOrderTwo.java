package com.example.trucksharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.trucksharing.databinding.ActivityNewOrderBinding;
import com.example.trucksharing.databinding.ActivityNewOrderTwoBinding;

public class NewOrderTwo extends AppCompatActivity {

    ActivityNewOrderTwoBinding binding;

    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_two);

        Intent getInent=getIntent();
        String Fname=getInent.getStringExtra("name");
        String PD=getInent.getStringExtra("pickDate");
        String T=getInent.getStringExtra("time");
        String LOC=getInent.getStringExtra("loc");


        binding= ActivityNewOrderTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDatabaseHelper=new MyDatabaseHelper(this);

        binding.CrateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=myDatabaseHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();

                contentValues.put("FullName",Fname);
                contentValues.put("PickupDate",PD);
                contentValues.put("Time",T);
                contentValues.put("Location",LOC);

                if (binding.furniture.isChecked()) {
                    contentValues.put("GoodType", "Furniture");
                } else if (binding.dryGoods.isChecked()) {
                    contentValues.put("GoodType", "Dry Goods");
                }else if (binding.food.isChecked()) {
                    contentValues.put("GoodType", "Food");
                }else if (binding.material.isChecked()) {
                    contentValues.put("GoodType", "Building Material");
                }
                else
                {
                    contentValues.put("GoodType", binding.goodother.getText().toString());
                }

                contentValues.put("Weight",binding.weight.getText().toString());
                contentValues.put("Width",binding.width.getText().toString());
                contentValues.put("Length",binding.length.getText().toString());
                contentValues.put("Height",binding.height.getText().toString());
//                contentValues.put("Vechile",binding.Rlocation.getText().toString());

                if (binding.truck.isChecked()) {
                    contentValues.put("Vechile", "Truck");
                } else if (binding.van.isChecked()) {
                    contentValues.put("Vechile", "Van");
                }else if (binding.truck.isChecked()) {
                    contentValues.put("Vechile", "Refridgerated Trucks");
                }else if (binding.minitruck.isChecked()) {
                    contentValues.put("Vechile", "Mini-Truck");
                }
                else
                {
                    contentValues.put("Vechile", binding.vechileother.getText().toString());
                }

                db.insert("myorder",null,contentValues);

                Intent GoToHome=new Intent(NewOrderTwo.this,Home.class);
                startActivity(GoToHome);
            }
        });
    }
}