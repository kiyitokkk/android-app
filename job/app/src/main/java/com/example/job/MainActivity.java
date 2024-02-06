package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.job.activity.BaseActivity;
import com.example.job.activity.HomeActivity;
import com.example.job.activity.LoginActivity;
import com.example.job.activity.RegisterActivity;
import com.example.job.util.StringUtils;

public class MainActivity extends BaseActivity {
    private String TAG;
    private Button btnLogin;
    private  Button btnRegister;
    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void intview() {
        btnLogin = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);
    }


    @Override
    protected void initData() {
        if(!StringUtils.isEmpty(getToken("token"))){
            jump(HomeActivity.class);
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(LoginActivity.class);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(RegisterActivity.class);
            }
        });
    }
}