package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Constant.SystemConstant;
import com.example.myapplication.R;
import com.example.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingFormatArgumentException;

public class MainActivity extends AppCompatActivity {
    private Button btnDN,btnDK;
    private EditText userName,passWord;
    private boolean isEnter;
    final LoadingDialog dialog = new LoadingDialog(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnDN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(passWord.getText().toString())){
                    Toast.makeText(MainActivity.this,"Bạn chưa nhập tài khoản hoặc mật khẩu",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, AfterloginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
//                    dialog.startLoadingDialog();
//                    DangNhap(SystemConstant.KEY_URL_Login);

                }

            }
        });

        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Registration.class));
            }
        });

    }
    private void init(){
        btnDN = (Button) findViewById(R.id.btnDangNhap);
        btnDK = (Button) findViewById(R.id.btnDangky);
        userName = (EditText) findViewById(R.id.user);
        passWord = (EditText) findViewById(R.id.password);
    }

    private void DangNhap (String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String message = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                User account = new User();
                                account.setUserName(jsonObject.getString("user_name"));
                                account.setEmail(jsonObject.getString("email"));
                                account.setFullname(jsonObject.getString("fullname"));
                                account.setSDT(jsonObject.getString("SDT"));

                                message = jsonObject.getString("message");
                                resetText();

                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                                //Start LoginActivity
                                startActivity(new Intent(MainActivity.this,AfterloginActivity.class));

                            } else {
                                message = jsonObject.getString("message");
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException error) {
                            error.getMessage();
                        }finally {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismissDialog();
                                }
                            },2000);
                        }
//                        if(response.trim().equals("Data Matched")) {
//                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(MainActivity.this,AfterloginActivity.class));
//                        }
//                        else if(response.trim().equals("Invalid Username or Password Please Try Again")){
//                            Toast.makeText(MainActivity.this,"failed", Toast.LENGTH_LONG).show();
//                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"loi ket noi", Toast.LENGTH_LONG).show();
                        dialog.dismissDialog();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(SystemConstant.KEY_USERNAME,userName.getText().toString().trim());
                params.put(SystemConstant.KEY_PASSWORD,passWord.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void resetText(){
        userName.setText("");
        passWord.setText("");
    }


}
