package com.example.canteenmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class introActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getSupportActionBar().hide();
        Thread thread= new Thread(){

            public void run(){
                try{
                    sleep(4000);

                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    Intent intent = new Intent(introActivity.this , MainActivity.class);
                    startActivity(intent);
                }
            }

        };thread.start();

    }
}