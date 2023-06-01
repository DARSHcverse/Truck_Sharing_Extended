package com.example.trucksharing;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trucksharing.databinding.ActivitySignupPageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SIGNUP_Page extends AppCompatActivity {

    ActivitySignupPageBinding binding;
    MyDatabaseHelper databaseHelper;

    private static final int PERMISSION_REQUEST_CODE = 1;

    String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
    private SQLiteDatabase readableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        Intent getfromMain=getIntent();

        binding= ActivitySignupPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper=new MyDatabaseHelper(this);



        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),PERMISSION_REQUEST_CODE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                        return;
                    }
                }

            }
        });


        binding.createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db=databaseHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();

                if((binding.password2.getText().toString()).equals(binding.confirmPassword.getText().toString()))
                {
                    contentValues.put("FullName",binding.fullname.getText().toString());
                    contentValues.put("UserName",binding.username2.getText().toString());
                    contentValues.put("Password",binding.password2.getText().toString());
                    contentValues.put("ConfirmPassword",binding.confirmPassword.getText().toString());
                    contentValues.put("PhoneNumber",binding.phone.getText().toString());
                    db.insert("mytable1",null,contentValues);

                    Intent intent=new Intent(SIGNUP_Page.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(SIGNUP_Page.this, "Passwords doesnt match!", Toast.LENGTH_SHORT).show();
                }



                Intent backToMain=new Intent(SIGNUP_Page.this,MainActivity.class);
                backToMain.putExtra("Key",contentValues);
                startActivity(backToMain);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            Uri uri=data.getData();
            binding.imageView.setImageURI(uri);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Media permission Approved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Media permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}