/* ----------------Student Details----------------
Name: Darshan Subramaniam; Id: 222550339
CourseName: SIT305
 */
package com.example.trucksharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trucksharing.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    EditText UserName,Password;
    Button Login,SignUp;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserName=findViewById(R.id.username);
        Password=findViewById(R.id.password);
        Login=findViewById(R.id.login);
        SignUp=findViewById(R.id.signup);


        databaseHelper = new MyDatabaseHelper(this);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String GetUser=UserName.getText().toString();
                String GetPass=Password.getText().toString();

                if (GetUser.equals("") || GetPass.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkUserNamePassword=databaseHelper.checkuserpass(GetUser,GetPass);
                    if(checkUserNamePassword==true)
                    {
                        Toast.makeText(MainActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent NextIntent=new Intent(MainActivity.this,NewOrder.class);
                        startActivity(NextIntent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent My_signup=new Intent(MainActivity.this,SIGNUP_Page.class);
                startActivity(My_signup);
            }
        });

    }

}