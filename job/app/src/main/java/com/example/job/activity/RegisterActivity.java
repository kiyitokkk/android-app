package com.example.job.activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.regex.Pattern;
import com.example.job.R;
import com.example.job.api.Api;
import com.example.job.api.Apiconfig;
import com.example.job.api.RequestCallback;
import com.example.job.entity.LoginResponse;
import com.example.job.entity.RegisterResponse;
import com.example.job.util.Cyber;
import com.example.job.util.StringUtils;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {
    private static String TAG;
    private EditText editAccount;
    private EditText editPwd;
    private String Account;
    private String Pwd;
    private Button btnRegister;
    private Button btnsend;
    private EditText email;
    private EditText code;
    private EditText verity;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void intview() {
        btnRegister = findViewById(R.id.register_btn);
        editAccount = findViewById(R.id.register_account);
        editPwd = findViewById(R.id.register_pwd);
        email = findViewById(R.id.register_email);
        verity = findViewById(R.id.verity);
        btnsend = findViewById(R.id.register_verity);
    }

    @Override
    protected void initData() {
        btnRegister.setOnClickListener(view -> {
            String account = editAccount.getText().toString().trim();
            String pwd = editPwd.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String endverity = verity.getText().toString().trim();
            VerityMail(account, pwd, mail, endverity);
        });
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = editAccount.getText().toString().trim();
                String mail = email.getText().toString().trim();
                SendMail(account, mail);
            }
        });
    }
    private void SendMail(String Account, String email) {
        if (StringUtils.isEmpty(Account)){
            showToast("请输入正确格式的账号");
            return;
        }
        if (!isValidEmail(email)){
            showToast("请输入正确格式的邮箱");
            return;
        }
        HashMap<String,Object> params = new HashMap <String,Object> ();
        params.put("account", Account);
        params.put("email", email);
        Api.config(Apiconfig.SEND_EMAIL, params).postRequest(mContext ,new RequestCallback() {
            @Override
            public void onSucess(String res) {
                Gson gson = new Gson();
                RegisterResponse registerResponse = gson.fromJson(res, RegisterResponse.class);
                if(registerResponse.getCode() == 0){
                    showToastSyc("发送成功，请在五分钟内填写");
                }else {
                    showToastSyc("网络开小差了～～～");
                }
            }
            @Override
            public void onFailure(Exception err) {
                Log.e(TAG, "onFailure: " );
            }
        });
    }


    private void VerityMail(String Account, String Pwd, String email, String verity) {
        if (StringUtils.isEmpty(Account)){
            showToast("请输入正确格式的账号");
            return;
        }
        if (StringUtils.isEmpty(verity)){
            showToast("验证码不能为空");
            return;
        }
        HashMap<String,Object> params = new HashMap <String,Object> ();
        params.put("account", Account);
        params.put("code", verity);
        Api.config(Apiconfig.VERITY_EMAIL, params).postRequest(mContext ,new RequestCallback() {
            @Override
            public void onSucess(String res) {
                Gson gson = new Gson();
                RegisterResponse registerResponse = gson.fromJson(res, RegisterResponse.class);
                if(registerResponse.getCode() == 0){
                    Register(Account, Pwd, email);
                    showToastSyc("验证成功");
                }else {
                    showToastSyc("网络开小差了～～～");
                }
            }

            @Override
            public void onFailure(Exception err) {
                Log.e(TAG, "onFailure: " );
            }
        });
    }

    private void Register(String Account, String Pwd, String email) {
            if (StringUtils.isEmpty(Account)){
                showToast("请输入正确格式的账号");
                return;
            }
            if (StringUtils.isEmpty(Pwd)){
                showToast("请输入正确的密码");
                return;
            }
            HashMap<String,Object> params = new HashMap <String,Object> ();
            params.put("account", Account);
            params.put("p", Cyber.hello1(Pwd));
            params.put("email", email);
            Api.config(Apiconfig.REGISTER_URL, params).postRequest(mContext ,new RequestCallback() {
                @Override
                public void onSucess(String res) {
                    Gson gson = new Gson();
                    RegisterResponse registerResponse = gson.fromJson(res, RegisterResponse.class);
                    if(registerResponse.getCode() == 0){
                        jump(LoginActivity.class);
                        showToastSyc("注册成功");
                    }else {
                        showToastSyc("注册失败，该帐号已被注册");
                    }
                }
                @Override
                public void onFailure(Exception err) {
                    Log.e(TAG, "onFailure: " );
                }
            });
        }
}
