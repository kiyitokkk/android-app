package com.example.job.fragment;

import static android.content.Context.MODE_PRIVATE;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import xyz.doikki.videoplayer.player.VideoViewManager;

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
//    private Unbinder unbinder;
    protected abstract int initLayout();
    protected abstract void intview();
    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(initLayout(),container,false);
            intview();
        }


        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }
    public String getUser(String key){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp_user", MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    public void navigateToWithFlag(Class cls, int flags) {
        Intent in = new Intent(getActivity(), cls);
        in.setFlags(flags);
        startActivity(in);
    }
    protected void removeToken(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_token", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }
    public void showToast(String msg){
        Toast toast =  Toast.makeText(getActivity(),null,Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    public void showToastSyc(String msg){
        Looper.prepare();
        Toast toast =  Toast.makeText(getActivity(),null,Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
        Looper.loop();
    }
    public void jump(Class cla){
        Intent intent = new Intent(getActivity(), cla);
        startActivity(intent);
    }
    protected void saveToken(String key, String val){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,val);
        editor.commit();
    }
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp_token", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    protected VideoViewManager getVideoViewManager() {
        return VideoViewManager.instance();
    }
}
