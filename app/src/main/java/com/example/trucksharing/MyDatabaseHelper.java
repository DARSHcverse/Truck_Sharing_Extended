package com.example.trucksharing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String MyDatabase="mydatabase.db";

    public static final int VERSION =3;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, MyDatabase, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE mytable (id INTEGER PRIMARY KEY, FullName TEXT, UserName TEXT, Password TEXT, ConfirmPassword TEXT, PhoneNumber TEXT)");
        db.execSQL("CREATE TABLE myorder (id INTEGER PRIMARY KEY, FullName TEXT, PickupDate TEXT, Time TEXT, Location TEXT, GoodType TEXT, Weight TEXT, Width TEXT, Length TEXT, Height TEXT, Vechile TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mytable");
    }
    public Boolean checkuserpass(String username,String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from mytable where UserName = ? and Password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else{
            return false;
        }
    }
    public Cursor getdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM mytable",null);
        return cursor;
    }
    public Cursor getdata2()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM myorder",null);
        return cursor;
    }


}
