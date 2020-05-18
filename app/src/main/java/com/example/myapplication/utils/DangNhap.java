package com.example.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Activity.AfterloginActivity;
import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Map;

public class DangNhap extends AppCompatActivity {
    private EditText userName,passWord;
    private Context myContext;
    public DangNhap(Context context){
        this.myContext = context;
    }
    public void dangNhap(String url) {

        userName = (EditText) findViewById(R.id.user);
        passWord = (EditText) findViewById(R.id.password);

        RequestQueue requestQueue = Volley.newRequestQueue(myContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Data Matched")) {
                            Toast.makeText(myContext, "Success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(myContext, AfterloginActivity.class));
                        }
                        else if(response.trim().equals("Invalid Username or Password Please Try Again")){
                            Toast.makeText(myContext,"failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myContext,"loi ket noi", Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userName",userName.getText().toString().trim());
                params.put("passWord",passWord.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
