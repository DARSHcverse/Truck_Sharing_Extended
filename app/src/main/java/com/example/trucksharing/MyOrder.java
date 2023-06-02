package com.example.trucksharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyOrder extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> Name, Pickuptime,DropOffTime,Locations,GoodTypes,Weights,Widths,Lengths,Heights,Vechiles,DRLoc;
    MyDatabaseHelper databaseHelper;
    ArrayList<String> name;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        databaseHelper=new MyDatabaseHelper(this);

        Name=new ArrayList<>();
        Pickuptime =new ArrayList<>();
        DropOffTime=new ArrayList<>();
        Locations=new ArrayList<>();
        GoodTypes=new ArrayList<>();
        Weights=new ArrayList<>();
        Widths=new ArrayList<>();
        Lengths=new ArrayList<>();
        Heights=new ArrayList<>();
        Vechiles=new ArrayList<>();
        DRLoc=new ArrayList<>();

        name=new ArrayList<>();

        recyclerView=findViewById(R.id.recyclerView2);

        ShowData();

        adapter=new MyAdapter(this,Name, Pickuptime,DropOffTime);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Truck Details");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(MyOrder.this,OrderDetails.class);
//                intent.putExtra("username",name.get(0));
                intent.putExtra("Rname",Name.get(position));
                intent.putExtra("pickup",DropOffTime.get(position));
                intent.putExtra("droptime",DropOffTime.get(position));
                intent.putExtra("weight",Weights.get(position));
                intent.putExtra("type",GoodTypes.get(position));
                intent.putExtra("width",Widths.get(position));
                intent.putExtra("heights",Heights.get(position));
                intent.putExtra("length",Lengths.get(position));
                intent.putExtra("vechi",Vechiles.get(position));
                intent.putExtra("loc",Locations.get(position));
                intent.putExtra("Dloc",DRLoc.get(position));
                startActivity(intent);
            }
        });



        FloatingActionButton floatingActionButton=findViewById(R.id.floatingActionButton3);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MyOrder.this,NewOrder.class);
                startActivity(intent2);
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId==R.id.Menu_Home)
        {
            Intent intent=new Intent(MyOrder.this,Home.class);
            startActivity(intent);
            return true;
        }
        else if(itemId==R.id.Menu_Account)
        {
            return true;

        } else if (itemId==R.id.Menu_My_orders) {
            Intent intent=new Intent(MyOrder.this,MyOrder.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void ShowData() {
        Cursor cursor=databaseHelper.getdata3();

        while(cursor.moveToNext())
        {
            Name.add(cursor.getString(1));
            Pickuptime.add(cursor.getString(2));
            DropOffTime.add(cursor.getString(3));
            Locations.add(cursor.getString(4));
            DRLoc.add(cursor.getString(5));
            GoodTypes.add(cursor.getString(6));
            Weights.add(cursor.getString(7));
            Widths.add(cursor.getString(8));
            Lengths.add(cursor.getString(9));
            Heights.add(cursor.getString(10));
            Vechiles.add(cursor.getString(11));
        }

    }
}