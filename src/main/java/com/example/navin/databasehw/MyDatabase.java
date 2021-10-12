package com.example.navin.databasehw;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by navin on 4/1/2017.
 */

public class MyDatabase extends SQLiteOpenHelper
{  Context context;
    MyDatabase( Context context)
    {
        super(context,"STUDENT",null,1);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String qry="create table student(idno number,name text,age number,contact  text,Email text primary key,password text,status text)";
         db.execSQL(qry);
       // Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
