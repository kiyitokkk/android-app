package com.example.job.activity;

import com.bumptech.glide.Glide;
import com.example.job.R;
import com.example.job.api.Api;
import com.example.job.api.Apiconfig;
import com.example.job.api.RequestCallback;
import com.example.job.entity.ContentType;
import com.example.job.util.Cyber;
import com.example.job.util.VideoUtils;
import okhttp3.*;

import androidx.annotation.Nullable;
import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.doikki.videoplayer.player.VideoView;


import android.database.Cursor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReleaseActivity extends BaseActivity{

    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int REQUEST_VIDEO_PICK = 2;
    private static final int REQUEST_PERMISSION = 3;
    private static final int REQUEST_IMAGE_PICK = 4;
    public static final int SELECT_PHOTO = 5;
    private VideoView videoPreviewImageView;
    private EditText editTextTitle;
    private ImageView imageViewSelectedImage;
    private TextView textViewVideoPath;
    private TextView textViewResult;
    private TextView textViewimgPath;
    private String imgPath;
    private String videoPath;
    private Button btnOpenDialog;
    @Override
    protected int initLayout() {
        return R.layout.activity_release;
    }

    @Override
    protected void intview() {
        editTextTitle = findViewById(R.id.editTextTitle);
        imageViewSelectedImage = findViewById(R.id.imageViewSelectedImage);
        textViewVideoPath = findViewById(R.id.textViewVideoPath);
        textViewResult = findViewById(R.id.textViewResult);
        textViewimgPath = findViewById(R.id.textViewimgPath);
        btnOpenDialog = findViewById(R.id.videotype);
    }

    @Override
    protected void initData() {
    }
    public void chooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PHOTO);
    }

    public void chooseVideo(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_VIDEO_PICK);
    }

    public void recordVideo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        } else {
            Toast.makeText(this, "No video recording app available", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadVideo(View view) {
        String title = editTextTitle.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Title is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        if (videoPath == null) {
            Toast.makeText(this, "Video is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        File videoFile = new File(videoPath);

        if (!videoFile.exists()) {
            Toast.makeText(this, "Video file not found", Toast.LENGTH_SHORT).show();
            return;
        }

        File imageFile = new File(imgPath);

        if (!imageFile.exists()) {
            Toast.makeText(this, "Image file not found", Toast.LENGTH_SHORT).show();
            return;
        }
        showToast("正在发布请稍后");
        OkHttpClient client = new OkHttpClient();
        SharedPreferences sp = this.getSharedPreferences("sp_token", MODE_PRIVATE);
        String tim = String.valueOf(System.currentTimeMillis());
        String token = sp.getString("token", "");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user", getUser("account"))
                .addFormDataPart("title", title)
                .addFormDataPart("img", imgPath, RequestBody.create(MediaType.parse("image/*"), new File(imgPath)))
                .addFormDataPart("types", btnOpenDialog.getText().toString())
                .addFormDataPart("video", videoFile.getName(),
                        RequestBody.create(MediaType.parse("video/mp4"), videoFile))
                .build();
        Request request = new Request.Builder()
                .addHeader("token",token)
                .addHeader("x-s", Cyber.hello2(tim))
                .addHeader("tim", tim)
                .url(Apiconfig.BASE_URL + Apiconfig.RELEASE_VIDEO)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 处理请求失败的情况
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 处理请求成功的情况
                if (response.isSuccessful()) {
                    // 请求成功
                    String responseBody = response.body().string();
                    showToastSyc(responseBody);
                    // 处理响应数据
                } else {
                    // 请求失败
                    // 处理响应错误
                }
            }
        });


    }
    private void loadImage(String imagePath) {
        if (imagePath != null) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Glide.with(this).load(imageFile).into(imageViewSelectedImage);
            }
        }
    }
    public void settype(View view) {
        // 创建底部弹出框
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_type);

        // 设置弹出框的样式
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置其他样式和内容

        // 显示弹出框
        dialog.show();
    }
    public void onOptionClicked(View view) {
        String selectedOption = ((TextView) view).getText().toString();

        btnOpenDialog.setText(selectedOption);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showToast(String.valueOf(requestCode));
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_VIDEO_CAPTURE) {
                Uri videoUri = data.getData();
                videoPath = getPathFromUri(videoUri);
                textViewVideoPath.setText(videoPath);
                enableUploadButton();
            } else if (requestCode == REQUEST_VIDEO_PICK) {
                Uri videoUri = data.getData();
                videoPath = getPathFromUri(videoUri);
                textViewVideoPath.setText(videoPath);
                enableUploadButton();
            }
            else if (requestCode == SELECT_PHOTO) {
                Uri imageUri = data.getData();
                imgPath = getPathFromUri(imageUri);
                textViewimgPath.setText(imgPath);
                loadImage(imgPath);
            }
        }

    }
    private void handleVideoPickerResult(Uri videoUri) {

    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

    private void enableUploadButton() {
        Button buttonUpload = findViewById(R.id.buttonUpload);
        buttonUpload.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
