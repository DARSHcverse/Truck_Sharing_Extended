package com.example.trucksharing;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> Name,PickUPDate, Pickuptime,Locations,GoodTypes,Weights,Widths,Lengths,Heights,Vechiles;
    ArrayList<String> name;
    ArrayList<Integer> Id;
    MyDatabaseHelper databaseHelper;

    MyAdapter2 myAdapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        databaseHelper=new MyDatabaseHelper(this);

        Name=new ArrayList<>();
        PickUPDate=new ArrayList<>();
        Pickuptime =new ArrayList<>();
        Locations=new ArrayList<>();
        GoodTypes=new ArrayList<>();
        Weights=new ArrayList<>();
        Widths=new ArrayList<>();
        Lengths=new ArrayList<>();
        Heights=new ArrayList<>();
        Vechiles=new ArrayList<>();

        name=new ArrayList<>();


        recyclerView=findViewById(R.id.recyclerView);

        ShowData();

        Cursor cursor=databaseHelper.getdata2();

        int vehicleColumnIndex = cursor.getColumnIndex("Vechile");

        int idColumnIndex = cursor.getColumnIndex("id");

        Id=new ArrayList<>();

        while (cursor.moveToNext()) {

            String vehicle = cursor.getString(vehicleColumnIndex);

            if (vehicle.equals("Truck")) {

                int id = cursor.getInt(idColumnIndex);
                Id.add(id);

                System.out.println(Id);
            }
        }
        cursor.close();

        myAdapter2=new MyAdapter2(this,Vechiles,PickUPDate, Pickuptime,Id);
        recyclerView.setAdapter(myAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter2.setOnItemClickListener(new MyAdapter2.OnItemClickListener() {
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


        myAdapter2.setOnItemClickListener(new MyAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(Home.this,OrderDetails.class);
                intent.putExtra("Rname",Name.get(position));
                intent.putExtra("pickup",Pickuptime.get(position));
                intent.putExtra("droptime",Pickuptime.get(position));
                intent.putExtra("weight",Weights.get(position));
                intent.putExtra("type",GoodTypes.get(position));
                intent.putExtra("width",Widths.get(position));
                intent.putExtra("heights",Heights.get(position));
                intent.putExtra("length",Lengths.get(position));
                intent.putExtra("vechi",Vechiles.get(position));
                startActivity(intent);

            }
        });




        FloatingActionButton floatingActionButton=findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Home.this,NewOrder.class);
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
            Intent intent=new Intent(Home.this,Home.class);
            startActivity(intent);
            return true;
        }
        else if(itemId==R.id.Menu_Account)
        {
            return true;

        } else if (itemId==R.id.Menu_My_orders) {
            Intent intent=new Intent(Home.this,MyOrder.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ShowData() {
        Cursor cursor=databaseHelper.getdata2();

        while(cursor.moveToNext())
        {
            Name.add(cursor.getString(1));
            PickUPDate.add(cursor.getString(2));
            Pickuptime.add(cursor.getString(3));
            Locations.add(cursor.getString(4));
            GoodTypes.add(cursor.getString(5));
            Weights.add(cursor.getString(6));
            Widths.add(cursor.getString(7));
            Lengths.add(cursor.getString(8));
            Heights.add(cursor.getString(9));
            Vechiles.add(cursor.getString(10));
        }

    }

//    private void ShowData2(){
//        Cursor cursor=databaseHelper.getdata();
//
//        while (cursor.moveToNext())
//        {
//            name.add(cursor.getString(1));
//        }
//    }



}