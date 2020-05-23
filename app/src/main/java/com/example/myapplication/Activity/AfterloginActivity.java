package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.google.gson.Gson;

public class AfterloginActivity extends AppCompatActivity {
    Button btnGo1 , btnGo2 ,btnGo3,  btnGo4  ;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        btnGo1 = (Button)findViewById(R.id.btnGo1);
        btnGo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Act1 = new Intent(AfterloginActivity.this, ActivityOne.class);
                startActivity(Act1);
            }
        });

        btnGo2 = (Button)findViewById(R.id.btnGo2);
        btnGo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Act1 = new Intent(AfterloginActivity.this, ActivityTwo.class);
                startActivity(Act1);
            }
        });

        btnGo3 = (Button)findViewById(R.id.btnGo3);
        btnGo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Act1 = new Intent(AfterloginActivity.this, ActivityThree.class);
                startActivity(Act1);
            }
        });

        btnGo4 = (Button)findViewById(R.id.btnGo4);
        btnGo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Act1 = new Intent(AfterloginActivity.this, ActivityFour.class);
                startActivity(Act1);
            }
        });


    }

}
