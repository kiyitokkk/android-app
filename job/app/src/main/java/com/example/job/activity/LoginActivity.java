package com.example.job.activity;
import com.example.job.MainActivity;
import com.example.job.api.Api;
import com.example.job.api.Apiconfig;
import com.example.job.api.RequestCallback;
import com.example.job.entity.LoginResponse;
import com.example.job.util.AppConfig;
import com.example.job.util.Cyber;
import com.example.job.util.StringUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.example.job.R;
import com.google.gson.Gson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private static String TAG;
    private EditText editAccount;
    private EditText editPwd;
    private Button btnLogin;
    private TextView findpassword;


    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void intview() {
        btnLogin = findViewById(R.id.login_btn);
        editAccount = findViewById(R.id.account_login);
        editPwd = findViewById(R.id.account_pwd);
        findpassword = findViewById(R.id.findpassword);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(view -> {
            String account = editAccount.getText().toString().trim();
            String pwd = editPwd.getText().toString().trim();
            Login(account, pwd);
        });
        findpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               jump(LossActivity.class);
            }
        });
    }
    private void Login(String Account, String Pwd) {
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
        params.put("m", Cyber.hello1(Pwd));
        Api.config(Apiconfig.LOGIN_URL, params).postRequest(mContext , new RequestCallback() {
            @Override
            public void onSucess(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        showToast(res);
                        setUser("account",Account);
                        Log.e(TAG, "run: " + res );
                    }
                });
                Gson gson = new Gson();
                LoginResponse LoginResponse = gson.fromJson(res, LoginResponse.class);
//               设置token
                if(LoginResponse.getCode() == 0){
                    String token = LoginResponse.getToken();
                    saveToken("token", token);
//                    jump(HomeActivity.class);
                    navigateToWithFlag(HomeActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

//                    SharedPreferences sharedPreferences = getSharedPreferences("sp_token", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("token",token);
//                    editor.commit();
                    showToastSyc("登陆成功");
                }else if (LoginResponse.getCode() == -2){
                    showToastSyc("帐号已被封禁，请联系管理员!");
                }else{
                    showToastSyc("帐号或密码错误");
                }
            }

            @Override
            public void onFailure(Exception err) {
                Log.e(TAG, "onFailure: " );
            }
        });
// {"code": 0, "expire": "123456", "msg": "sucess", "token": "dawdawdaghdkjawhdkjagwdjkawghjdawjdawdaw"}
//        JSONObject jsonObject = new JSONObject(map);
//        String jsonstr = jsonObject.toString();
//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);
//        Request request = new Request.Builder()
//                .addHeader("contentType" ,"application/json; charset=utf-8")
//                .post(body)
//                .url(AppConfig.BASE_URL + "/app/login")
//                .build();
//        Call call = client.newCall(request);
//        //返回请求结果
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e(TAG, "onFailure: " );
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.e(TAG, "onResponse: " );
//                String result = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(result);
//                    }
//                });
//            }
//        });
//        String response =
    }

}