package com.example.navin.databasehw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LgsnActivity extends AppCompatActivity {
    Button Lgn, Sgup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lgsn);
        Lgn = findViewById(R.id.sin);
        Sgup = findViewById(R.id.sup);
        Lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LgsnActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        Sgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LgsnActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
