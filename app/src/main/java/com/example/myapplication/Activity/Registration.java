package com.example.myapplication.Activity;

import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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

public class Registration extends AppCompatActivity {
    private Button btnDK,btnHuy;
    private EditText user, pass, fullname, SDT,email;
    private AlertDialog.Builder builder;
    final LoadingDialog dialogS = new LoadingDialog(Registration.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Tạo tài khoản");

        init();
        builder = new AlertDialog.Builder(this);
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(user.getText().toString()) || TextUtils.isEmpty(pass.getText().toString())
                ||TextUtils.isEmpty(fullname.getText().toString()) || TextUtils.isEmpty(SDT.getText().toString())
                || TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(Registration.this,"Bạn cần phải nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }
                else {
                    if(!CheckMail(email.getText().toString())){
                        Toast.makeText(Registration.this,"Mail không hợp lệ",Toast.LENGTH_LONG).show();

                    }
                    else{
                        builder.setMessage("Bạn có muốn đăng ký tài khoản này không ?").
                                setCancelable(false).
                                setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialogS.startLoadingDialog();
                                        DangKy(SystemConstant.KEY_URL_Registration);
                                    }
                                })
                                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Toast.makeText(Registration.this,"Không tạo tài khoản", Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setTitle("Xác nhận tạo tài khoản");
                        alertDialog.show();

                    }

                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void DangKy(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(Registration.this);
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
                                Toast.makeText(Registration.this, message, Toast.LENGTH_LONG).show();
                                //Start LoginActivity
                                Intent intent = new Intent(Registration.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                message = jsonObject.getString("message");
                                Toast.makeText(Registration.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException error) {
                            error.getMessage();
                        } finally {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialogS.dismissDialog();
                                }
                            },2000);
                        }
//                        if(response.trim().equals("Registration Successfully")) {
//                            Toast.makeText(Registration.this, "Registration Successfully", Toast.LENGTH_LONG).show();
//                            finish();
//                        }
//                        else if(response.trim().equals("failed")){
//                            Toast.makeText(Registration.this,"failed", Toast.LENGTH_LONG).show();
//                            resetText();
//                        }
//                        else if(response.trim().equals("User Already Exist")){
//                            Toast.makeText(Registration.this,"User Already Exist", Toast.LENGTH_LONG).show();
//                            resetText();
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registration.this,"loi ket noi", Toast.LENGTH_LONG).show();
                dialogS.dismissDialog();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(SystemConstant.KEY_USERNAME,user.getText().toString().trim());
                params.put(SystemConstant.KEY_PASSWORD,pass.getText().toString().trim());
                params.put(SystemConstant.KEY_FULLNAME,fullname.getText().toString().trim());
                params.put(SystemConstant.KEY_SDT,SDT.getText().toString().trim());
                params.put(SystemConstant.KEY_EMAIL,email.getText().toString().trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void init(){
        btnDK = (Button) findViewById(R.id.btnDK);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        user = (EditText) findViewById(R.id.txtUsername);
        pass = (EditText) findViewById(R.id.txtPassword);
        fullname = (EditText) findViewById(R.id.txtHovaTen);
        SDT = (EditText) findViewById(R.id.txtSDT);
        email = (EditText) findViewById(R.id.txtEmail);
    }

    private void resetText(){
        user.setText("");
        pass.setText("");
        fullname.setText("");
        SDT.setText("");
        email.setText("");
    }

    private boolean CheckMail(String target) {
        if (target.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
            return true;
        return false;
    }
}
