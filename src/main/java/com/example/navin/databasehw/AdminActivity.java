package com.example.navin.databasehw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
    public void acceptedEmployee(View v)
    {
        Intent i=new Intent(this,listAcceptedEmployee.class);
        startActivity(i);
    }
    public void pendingEmployee(View v)
    {
     Intent i=new Intent(this,listpendingEmployee.class);
        startActivity(i);
    }
    public void declineEmployee(View v)
    {
        Intent i=new Intent(this,listDeclineEmployee.class);
        startActivity(i);
    }
}
