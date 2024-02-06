package com.example.job.activity;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.job.MainActivity;
import com.example.job.R;
import com.google.android.exoplayer2.extractor.mp4.Track;
import com.hjq.window.EasyWindow;
import com.hjq.window.draggable.SpringDraggable;

import java.security.MessageDigest;
import java.util.List;

import xyz.doikki.videoplayer.player.VideoViewManager;

public abstract class BaseActivity extends AppCompatActivity {

    private int numberMask;
    public Context mContext;
    public ImageView imageView ;
    private EasyWindow easyWindow;
    protected VideoViewManager getVideoViewManager() {
        return VideoViewManager.instance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initLayout());
        intview();
        initData();
    }
    public void navigateToWithFlag(Class cls, int flags) {
        Intent in = new Intent(mContext, cls);
        in.setFlags(flags);
        startActivity(in);
    }
    protected abstract int initLayout();
    protected abstract void intview();
    protected abstract void initData();
    public void showToast(String msg){
        Toast toast =  Toast.makeText(mContext,null,Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    public void showToastSyc(String msg){
        Looper.prepare();
        Toast toast =  Toast.makeText(mContext,null,Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
        Looper.loop();


    }
    public void jump(Class cla){
        Intent intent = new Intent(mContext, cla);
        startActivity(intent);
    }
    protected void saveToken(String key, String val){
        SharedPreferences sharedPreferences = getSharedPreferences("sp_token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,val);
        editor.commit();
    }
    protected void setUser(String key, String val){
        SharedPreferences sharedPreferences = getSharedPreferences("sp_user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,val);
        editor.commit();
    }
    public String getUser(String key){
        SharedPreferences sharedPreferences = getSharedPreferences("sp_user", MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    protected void removeUser(String key) {
        SharedPreferences sp = getSharedPreferences("sp_user", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }
//    protected void saveToken(String key, String val){
//        SharedPreferences sharedPreferences = getSharedPreferences("sp_token", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(key,val);
//        editor.commit();
//    }
    public void setDialog(Context context, String msg){
        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
        builder2.setTitle(msg)
                .setMessage(msg)
                .setPositiveButton("是", null)
                .setNegativeButton("否", null)
                .setCancelable (false)
                .create()
                .show();
    }
    public String getToken(String key){
        SharedPreferences sharedPreferences = getSharedPreferences("sp_token", MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    public static String getCurrentActivityName(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Activity.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getClassName();
    }


    @Override
    protected void onResume() {
        super.onResume();
        String activity = BaseActivity.getCurrentActivityName(this);
        if(easyWindow == null && !activity.equals("com.example.job.MainActivity") && !activity.equals("com.example.job.activity.LoginActivity") && !activity.equals("com.example.job.activity.RegisterActivity")){
            easyWindow = new EasyWindow<>(this);
            easyWindow.setContentView(R.layout.add_message)
                    .setGravity(Gravity.END | Gravity.BOTTOM)
                    .setYOffset(200)
                    // 设置指定的拖拽规则
                    .setDraggable(new SpringDraggable(SpringDraggable.ORIENTATION_HORIZONTAL))
                    .setOnClickListener(android.R.id.icon, new EasyWindow.OnClickListener<ImageView>() {
                        @Override
                        public void onClick(EasyWindow<?> window, ImageView view) {
                            jump(ReleaseActivity.class);
                            // 点击后跳转到拨打电话界面
                            // Intent intent = new Intent(Intent.ACTION_DIAL);
                            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            // toast.startActivity(intent);
                            // 安卓 10 在后台跳转 Activity 需要额外适配
                            // https://developer.android.google.cn/about/versions/10/privacy/changes#background-activity-starts
                        }
                    })
                    .show();
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
//        if (floatingView != null) {
//            floatingView.dismissFloatView();
//            floatingView = null;
//        }
    }
}
