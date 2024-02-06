package com.example.job.util;

import com.example.job.api.Api;
import com.example.job.api.Apiconfig;

import java.io.File;
import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VideoUtils {
    public static void uploadVideo(File videoFile, String title, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        // 创建请求体
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("video", videoFile.getName(),
                        RequestBody.create(MediaType.parse("video/mp4"), videoFile))
                .build();
        // 创建请求
        Request request = new Request.Builder()
                .url(Apiconfig.BASE_URL + Apiconfig.RELEASE_VIDEO) // 根据你的服务器端点进行适当的更改
                .post(requestBody)
                .build();
        // 发送请求
        client.newCall(request).enqueue(callback);
    }
}
