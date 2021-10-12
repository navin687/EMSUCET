package com.example.navin.databasehw;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;

import static java.security.AccessController.getContext;

public class SplashActivity extends AppCompatActivity {
    ImageView splimg;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splimg = findViewById(R.id.splimg);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        //  progressStatus = 0;
        session = new SessionManager(getApplicationContext());
        Glide.with(this)
                .load(R.drawable.ldmm)
                //  .placeholder(R.drawable.imgbnrplaceh)
                //.asBitmap()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(splimg);
       // session.checkLogin();
        if(session.isLoggedIn() == true){
            run();
        }
        else{
            run2();
        }

        //run();



/*

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 120) {
                    progressStatus += 1;

                    // Try to sleep the thread for 20 milliseconds
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(progressStatus);
                            Intent i = new Intent(SplashActivity.this, LgsnActivity.class);
                            startActivity(i);

                            // Show the progress on TextView
                            //   tv.setText(progressStatus+"");
                        }
                    });
                }
            }
        }).start();

        */

    }

    private void run() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, NavDrwActivity.class);
                startActivity(i);
                finish();
            }
        }, 2500);
    }

    private void run2() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LgsnActivity.class);
                startActivity(i);
                finish();
            }
        }, 2500);
    }

}

