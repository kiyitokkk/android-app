package com.example.job.api;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.job.activity.LoginActivity;
import com.example.job.util.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.example.job.activity.BaseActivity;
import com.example.job.util.Cyber;
import com.example.job.util.FingerPrint;
import com.example.job.util.StringUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api{
    private static String requestUrl;
    public static OkHttpClient client;
    private static HashMap<String,Object> paramss;
    public static Api api = new Api();

    public  Api(){

    }
    public static Api config(String url, HashMap<String,Object> params){
        client = new OkHttpClient.Builder()
                .build();
        requestUrl = AppConfig.BASE_URL +  url;
        paramss= params;
        return api;
    }
    public static void postRequestVideo(Context context, RequestCallback callback, String title, File videoFile){

    }
    public static void postRequest(Context context, RequestCallback callback){
        JSONObject jsonObject = new JSONObject(paramss);
        String jsonstr = jsonObject.toString();
        SharedPreferences sp = context.getSharedPreferences("sp_token", MODE_PRIVATE);
        String token = sp.getString("token", "");
        String tim = String.valueOf(System.currentTimeMillis());
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String fingerPrint = FingerPrint.getFinger(tim);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);
        Request request = new Request.Builder()
                .addHeader("contentType" ,"application/json; charset=utf-8")
                .addHeader("token",token)
                .addHeader("fingerprint", fingerPrint)
                .addHeader("x-s", Cyber.hello2(tim))
                .addHeader("tim", tim)
                .post(body)
                .url(requestUrl)
                .build();
        Call call = client.newCall(request);
        //返回请求结果
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callback.onSucess(result);
            }
        });
    }
    public void showToastSyc(Context context,String msg){
        Looper.prepare();
        Toast toast =  Toast.makeText(context,null,Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
        Looper.loop();
    }
    public void getRequest(Context context,RequestCallback callback) {
        SharedPreferences sp = context.getSharedPreferences("sp_token", MODE_PRIVATE);
        String token = sp.getString("token", "");
        String tim = String.valueOf(System.currentTimeMillis());
        String url = getAppendUrl(requestUrl, paramss);
        String fingerPrint = FingerPrint.getFinger(tim);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("fingerprint", fingerPrint)
                .addHeader("token",token)
                .addHeader("x-s", Cyber.hello2(tim))
                .addHeader("tim", tim)
                .get()
                .build();
        Call call = client.newCall(request);
        //返回请求结果
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("code");
                    if(code.equals("401")){
                        Intent in = new Intent(context, LoginActivity.class);
                        context.startActivity(in);
                    } else if (code.equals("405")) {
                        showToastSyc(context, "出现错误请稍后再试!!");
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
                callback.onSucess(result);
            }
        });
    }
    private String getAppendUrl(String url, Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                if (StringUtils.isEmpty(buffer.toString())) {
                    buffer.append("?");
                } else {
                    buffer.append("&");
                }
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
            }
            url += buffer.toString();
        }
        return url;
    }
}
