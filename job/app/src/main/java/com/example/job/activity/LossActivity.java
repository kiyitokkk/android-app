package com.example.job.activity;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.job.R;
import com.example.job.api.Api;
import com.example.job.api.Apiconfig;
import com.example.job.api.RequestCallback;
import com.example.job.entity.RegisterResponse;
import com.example.job.util.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.regex.Pattern;

public class LossActivity extends BaseActivity{
    private EditText AccountLoss;
    private EditText AccountEmail;
    private static String TAG;
    private EditText AccountVerity;
    private EditText EditPassword;
    private Button SendemailBtn;
    private Button VerityEdit;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_loss;
    }

    @Override
    protected void intview() {
        AccountLoss = findViewById(R.id.account_loss);
        AccountEmail = findViewById(R.id.account_email);
        AccountVerity = findViewById(R.id.account_verity);
        EditPassword = findViewById(R.id.edit_password);
        SendemailBtn = findViewById(R.id.sendemail_btn);
        VerityEdit = findViewById(R.id.verity_edit);
    }

    @Override
    protected void initData() {
        SendemailBtn.setOnClickListener(view -> {
            String account = AccountLoss.getText().toString().trim();
            String email = AccountEmail.getText().toString().trim();
            if (StringUtils.isEmpty(account)){
                showToast("请输入正确格式的账号");
                return;
            }
            if (!isValidEmail(email)){
                showToast("请输入正确格式的邮箱");
                return;
            }
            HashMap<String,Object> params = new HashMap <String,Object> ();
            params.put("account", account);
            params.put("email", email);
            Api.config(Apiconfig.EDIT_SEND_EMAIL, params).postRequest(mContext ,new RequestCallback() {
                @Override
                public void onSucess(String res) {
                    Gson gson = new Gson();
                    RegisterResponse registerResponse = gson.fromJson(res, RegisterResponse.class);
                    if(registerResponse.getCode() == 0){
                        showToastSyc("发送成功，请在五分钟内填写");
                    }else if (registerResponse.getCode() == -1){
                        showToastSyc(registerResponse.getMsg());
                    }
                }
                @Override
                public void onFailure(Exception err) {
                    Log.e(TAG, "onFailure: " );
                }
            });
        });
        VerityEdit.setOnClickListener(view -> {
            String account = AccountLoss.getText().toString().trim();
            String email = AccountEmail.getText().toString().trim();
            String accountVerity = AccountVerity.getText().toString().trim();
            String editPassword = EditPassword.getText().toString().trim();
            if (StringUtils.isEmpty(account)){
                showToast("请输入正确格式的账号");
                return;
            }
            if (!isValidEmail(email)){
                showToast("请输入正确格式的邮箱");
                return;
            }
            if (StringUtils.isEmpty(accountVerity)){
                showToast("请输入验证码");
                return;
            }
            if (StringUtils.isEmpty(editPassword)){
                showToast("请输入密码");
                return;
            }
            HashMap<String,Object> params = new HashMap <String,Object> ();
            params.put("account", account);
            params.put("code", accountVerity);
            params.put("password", editPassword);
            Api.config(Apiconfig.PWD_VERITY_EMAIL, params).postRequest(mContext ,new RequestCallback() {
                @Override
                public void onSucess(String res) {
                    Gson gson = new Gson();
                    RegisterResponse registerResponse = gson.fromJson(res, RegisterResponse.class);
                    if(registerResponse.getCode() == 0){
                        jump(LoginActivity.class);
                        showToastSyc("密码修改成功，请重新登陆");
                    }else {
                        showToastSyc("网络开小差了～～～");
                    }
                }
                @Override
                public void onFailure(Exception err) {
                    Log.e(TAG, "onFailure: " );
                }
            });
        });
    }
}
