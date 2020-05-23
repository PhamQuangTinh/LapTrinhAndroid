package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AcvitityExtra.LoadingDialog;
import com.example.myapplication.Constant.SystemConstant;
import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btnDN,btnDK;
    private EditText userName,passWord;
    //Khai Bao de su dung chuc nang loading khi chuyen 1 activity
    final LoadingDialog dialog = new LoadingDialog(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Khoi tao gia tri cac phan tu trong activity
        init();
        //Nut Dang nhap
        btnDN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(passWord.getText().toString())){
                    Toast.makeText(MainActivity.this,"Bạn chưa nhập tài khoản hoặc mật khẩu",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, AfterloginActivity.class);
                    startActivity(intent);

//                    //Bat dau mo dialog loading
//                    dialog.startLoadingDialog();
//                    //Chuc Nang dang nhap
//                    DangNhap(SystemConstant.KEY_URL_Login);
//                    //Anomation chuyen trang
//                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                }

            }
        });
        //Nut Dang ky
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
        //Goi cac gia tri trong edittext len url co phuong thuc la POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String message = "";
                        try {
                            //Lay du lieu tra ve va do vao MODEL USER
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                User account = new User();
                                account.setUserName(jsonObject.getString("user_name"));
                                account.setEmail(jsonObject.getString("email"));
                                account.setFullname(jsonObject.getString("fullname"));
                                account.setSDT(jsonObject.getString("SDT"));
                                account.setPassWord(jsonObject.getString("password"));
                                message = jsonObject.getString("message");

                                //Sau Khi dang nhap, reset lai cac edit texyt
                                resetText();

                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                                //Start AfterloginActivity
                                Intent activity = new Intent(MainActivity.this,AfterloginActivity.class);
                                //Chuyen du lieu sang trang afterlogin
                                activity.putExtra("UserModel",  new Gson().toJson(account));
                                startActivity(activity);

                            } else {
                                message = jsonObject.getString("message");
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException error) {
                            error.getMessage();
                        }finally {
                            //Ket thuc anomation sau 2s
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
                //Gui thong tin du lieu len server
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

    //
    private void getBundleFromRegistration(){
        Bundle bundle = getIntent().getExtras();
        userName.setText(bundle.getString("taikhoan"));
        passWord.setText(bundle.getString("matkhau"));
    }

}
