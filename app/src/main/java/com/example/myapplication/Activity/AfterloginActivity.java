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
    private Button btnpfbutton;
    private String jsonUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jsonUser = extras.getString("UserModel");
        }
        User myUser = new Gson().fromJson(jsonUser, User.class);
        Toast.makeText(getApplicationContext(),myUser.getFullname(), Toast.LENGTH_LONG).show();
        btnpfbutton = (Button) findViewById(R.id.btnpfbutton);
        btnpfbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

}
